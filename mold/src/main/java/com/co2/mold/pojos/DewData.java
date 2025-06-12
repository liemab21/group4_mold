package com.co2.mold.pojos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DewData { // NUR TEST
    private double temperature;
    private double humidity;
    private LocalDate date; // Date bräuchte man ja für einen Forecast ?
}
