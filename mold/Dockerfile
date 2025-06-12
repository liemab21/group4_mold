#
# Dockerfile for production images
#
# @Author: Benjamin Senekowitsch
# @Version: 0.2
#

# ========================= #
#      BUILDING STAGE       #
# ========================= #

# Use Eclipse Temurin JDK 23 on Alpine Linux for the build stage
# JDK is required for compiling Java source code and extracting JAR layers
FROM eclipse-temurin:23-jdk-alpine AS build-stage

# Install Maven and other build dependencies
# unzip: needed for JAR layer extraction
# Maven: build tool for compiling and packaging the application
RUN apk add --no-cache maven unzip

# Set the working directory inside the container
WORKDIR /workspace

COPY /pom.xml ./pom.xml

# Download Maven dependencies
# This step is separated to leverage Docker layer caching
# Dependencies are downloaded only when pom.xml changes
RUN mvn dependency:go-offline -B

# Copy the source code
# This is done after dependency download to maximize cache efficiency
COPY ./src ./src

# Build the application
# -B: Batch mode (non-interactive)
# -DskipTests: Skip running tests during build (assumes tests run in CI/CD)
# clean: Remove previous build artifacts
# package: Compile, test, and package the application into JAR
RUN mvn clean package -B -DskipTests

# Extract JAR into layers using Spring Boot's layertools
# This separation allows for better Docker layer caching:
# - dependencies/ (external libraries - changes rarely)
# - spring-boot-loader/ (Spring Boot classes - changes rarely)
# - snapshot-dependencies/ (snapshot versions - changes occasionally)
# - application/ (your application code - changes frequently)
RUN java -Djarmode=layertools -jar target/*.jar extract



# ========================= #
#      RUNTIME STAGE        #
# ========================= #

# Use Eclipse Temurin JRE 23 on Alpine Linux for the runtime stage
# JRE is sufficient for running the application and reduces image size
FROM eclipse-temurin:23-jre-alpine AS runtime

# Install essential utilities:
# - dumb-init: Handles signals properly in containers (prevents zombie processes)
# - wget: Useful for health checks and debugging
RUN apk add --no-cache dumb-init wget

# Create a non-root user for security best practices
# Running as root in containers is a security risk
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory for the application
WORKDIR /app

# Copy extracted JAR layers from build stage in optimal order for caching
# Dependencies are copied first since they change less frequently than application code
# This maximizes Docker layer cache efficiency during rebuilds

# Copy external dependencies (rarely change)
COPY --from=build-stage --chown=spring:spring /workspace/dependencies/ ./

# Copy Spring Boot loader classes (rarely change)
COPY --from=build-stage --chown=spring:spring /workspace/spring-boot-loader/ ./

# Copy snapshot dependencies (change occasionally)
COPY --from=build-stage --chown=spring:spring /workspace/snapshot-dependencies/ ./

# Copy application code (changes most frequently)
COPY --from=build-stage --chown=spring:spring /workspace/application/ ./

# Switch to non-root user for security
USER spring

# JVM optimization flags for containerized environments
ENV JAVA_OPTS="\
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=70.0 \
    -XX:+UseG1GC \
    -XX:+UseStringDeduplication \
    -XX:+OptimizeStringConcat \
    -Xss256k \
    -XX:ReservedCodeCacheSize=64m \
    -XX:InitialCodeCacheSize=32m \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.awt.headless=true \
    -Dspring.backgroundpreinitializer.ignore=true"

# Explanation of JVM flags:
# -XX:+UseContainerSupport: Makes JVM aware of container memory limits
# -XX:MaxRAMPercentage=70.0: Uses max 70% of available container memory
# -XX:+UseG1GC: Uses G1 garbage collector (good for low-latency apps)
# -XX:+UseStringDeduplication: Reduces memory usage by deduplicating strings
# -XX:+OptimizeStringConcat: Optimizes string concatenation performance
# -Xss256k: Reduces thread stack size to save memory
# -XX:ReservedCodeCacheSize=64m: Limits JIT compiler code cache
# -XX:InitialCodeCacheSize=32m: Sets initial code cache size
# -Djava.security.egd=file:/dev/./urandom: Uses faster random number generation
# -Djava.awt.headless=true: Disables GUI components (not needed in containers)
# -Dspring.backgroundpreinitializer.ignore=true: Disables background initialization

# Spring Boot specific optimizations for production
ENV SPRING_OPTS="\
    -Dspring.jmx.enabled=false \
    -Dspring.profiles.active=prod \
    -Dmanagement.endpoints.jmx.exposure.exclude=*"

# Explanation of Spring Boot flags:
# -Dspring.jmx.enabled=false: Disables JMX (not needed in most containerized apps)
# -Dspring.profiles.active=prod: Activates production Spring profile
# -Dmanagement.endpoints.jmx.exposure.exclude=*: Disables JMX actuator endpoints

# Expose port 8080 (standard Spring Boot port)
# This is just documentation - actual port binding happens at runtime
EXPOSE 8080

# Use dumb-init as PID 1 for proper signal handling
# This ensures graceful shutdowns when the container receives SIGTERM
ENTRYPOINT ["dumb-init", "--"]

# Start the Spring Boot application using the JAR launcher
# Uses shell form to allow environment variable expansion
# Combines both JAVA_OPTS and SPRING_OPTS for complete configuration
CMD ["sh", "-c", "java ${JAVA_OPTS} ${SPRING_OPTS} org.springframework.boot.loader.launch.JarLauncher"]