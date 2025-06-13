package com.co2.mold.service;

import com.co2.mold.client.Co2Client;
import com.co2.mold.client.TemperatureClient;
import com.co2.mold.client.WeatherClient;
import com.co2.mold.model.dew.Dew;
import com.co2.mold.model.mold.Mold;
import com.co2.mold.model.mold.MoldRisk;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/*
    Error handling missing at the moment.
    Should be added later.

    If possible:
    Implement a logic to get or set classrooms dynamically over endpoints
 */

@Service
public class MoldRiskService {

    private final DewService dewService;
    private final MoldService moldService;
    private final Co2Client co2Client;
    private final TemperatureClient temperatureClient;
    private final WeatherClient weatherClient;

    // TODO: ADD DYNAMIC classroom management
    private final List<String> classrooms = List.of("EP01", "EP02", "EP03", "EP04");

    public MoldRiskService(DewService dewService, MoldService moldService, Co2Client co2Client,
                           TemperatureClient temperatureClient, WeatherClient weatherClient) {
        this.dewService = dewService;
        this.moldService = moldService;
        this.co2Client = co2Client;
        this.temperatureClient = temperatureClient;
        this.weatherClient = weatherClient;
    }
    @Scheduled(fixedRateString = "${mold.interval}")
    public void createMoldRiskEntry(){
        for (String classroom : classrooms) {
            try {
                // Get current data from external services
                LocalDateTime currentTime = LocalDateTime.now();
                Float temperature = temperatureClient.getCurrentTemperature(currentTime.toString(), classroom);
                Float humidity = weatherClient.getCurrentHumidity(currentTime.toString(), classroom);
                Float co2Level = co2Client.getCurrentCo2(currentTime.toString(), classroom);

                double dewPoint = DewService.calculateDewPoint(temperature, humidity);

                Dew dewEntry = Dew.builder()
                        .datetime(currentTime)
                        .temperature(temperature)
                        .humidity(humidity)
                        .dewPoint(dewPoint)
                        .build();

                MoldRisk riskLevel = calculateRiskLevelWithDewPoint(co2Level, dewEntry);

                Mold moldEntry = Mold.builder()
                        .classroom(classroom)
                        .moldRisk(riskLevel)
                        .datetime(LocalDateTime.now())
                        .build();

                moldService.insertMoldData(moldEntry);
                dewService.insertDewData(dewEntry);

            } catch (Exception e) {
                System.err.println("Error processing classroom " + classroom + ": " + e.getMessage());
            }
        }
    }

    public static MoldRisk calculateRiskLevelWithDewPoint(double co2Level, Dew dew) {
        int riskScore = 0;

        // Dew point is the most critical factor for mold risk
        // Higher dew point = higher moisture content = higher mold risk
        if (dew.getDewPoint() > 20) riskScore += 4;        // Very high moisture
        else if (dew.getDewPoint() > 16) riskScore += 3;   // High moisture
        else if (dew.getDewPoint() > 12) riskScore += 2;   // Moderate moisture
        else if (dew.getDewPoint() > 8) riskScore += 1;    // Low moisture
        // dewPoint <= 8: Very low moisture (no additional risk)

        // Temperature range optimal for mold growth (20-30°C)
        if (dew.getTemperature() >= 20 && dew.getTemperature() <= 30) riskScore += 2;
        else if (dew.getTemperature() >= 15 && dew.getTemperature() <= 35) riskScore += 1;

        // High CO2 can indicate poor ventilation
        // Parts per million
        if (co2Level > 2000) riskScore += 2;
        else if (co2Level > 1000) riskScore += 1;


        // Map total score to risk level
        return switch (riskScore) {
            case 0, 1 -> MoldRisk.VERY_LOW;
            case 2, 3 -> MoldRisk.LOW;
            case 4, 5 -> MoldRisk.MEDIUM;
            case 6 -> MoldRisk.HIGH;
            default -> MoldRisk.CRITICAL;
        };
    }
}
