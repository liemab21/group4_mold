package com.co2.mold.model.mold;

public enum MoldRisk {
    VERY_LOW("Very Low Risk"),
    LOW("Low Risk"),
    MEDIUM("Medium Risk"),
    HIGH("High Risk"),
    CRITICAL("Critical Risk");

    private final String description;

    MoldRisk(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}