package com.co2.mold.model.dew;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DewForecast {
    private LocalDate forecastDate;
    private double dewPoint;

    public DewForecast(LocalDate forecastDate, double dewPoint) {
        this.forecastDate = forecastDate;
        this.dewPoint = dewPoint;
    }

}