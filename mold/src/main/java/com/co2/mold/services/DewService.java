package com.co2.mold.services;

import com.co2.mold.pojos.DewData;
import com.co2.mold.pojos.DewForecast;
import com.co2.mold.repositories.DewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public DewForecast calculateForecast() {
        List<DewData> recentData = dewRepository.findTop10ByOrderByDateDesc();

        if (recentData.isEmpty()) {
            throw new IllegalStateException("no data found");
        }

        double avgDewPoint = recentData.stream()
                .mapToDouble(data -> calculateDewPoint(data.getTemperature(), data.getHumidity()))
                .average()
                .orElseThrow();

        return new DewForecast(LocalDate.now().plusDays(1), avgDewPoint);
    }
}

