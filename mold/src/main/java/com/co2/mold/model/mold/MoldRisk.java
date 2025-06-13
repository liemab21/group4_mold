package com.co2.mold.model.mold;

import lombok.Getter;

@Getter
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

    @Override
    public String toString() {
        return description;
    }
}