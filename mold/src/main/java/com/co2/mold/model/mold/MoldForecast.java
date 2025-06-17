package com.co2.mold.model.mold;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MoldForecast {
    private LocalDate forecastDate;
    private MoldRisk predictedRisk;

    public MoldForecast(LocalDate forecastDate, MoldRisk predictedRisk) {
        this.forecastDate = forecastDate;
        this.predictedRisk = predictedRisk;
    }

}
