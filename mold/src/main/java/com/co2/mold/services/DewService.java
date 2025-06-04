package com.co2.mold.services;

import com.co2.mold.repositories.DewRepository;
import org.springframework.stereotype.Service;

@Service
public class DewService {
    private final DewRepository dewRepository;

    public DewService(DewRepository dewRepository) {
        this.dewRepository = dewRepository;
    }

    public static double calculateDewPoint(double temperature, double humidity) {
        double a = 17.62;
        double b = 243.12;

        double alpha = (a * temperature) / (b + temperature) + Math.log(humidity / 100.0);
        return (b * alpha) / (a - alpha);
    }
}
