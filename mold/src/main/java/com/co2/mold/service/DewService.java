package com.co2.mold.service;

import com.co2.mold.model.dew.Dew;
import com.co2.mold.model.dew.DewForecast;
import com.co2.mold.model.mold.Mold;
import com.co2.mold.repository.DewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Dew> getAllDewData() {
        return dewRepository.findAll();
    }

    public List<Dew> getDewDataByDate(LocalDate date) {
        return dewRepository.findByDatetime(date);
    }

    public Dew insertDewData(Dew dew) {
        if (dew.getDatetime() == null) {
            dew.setDatetime(LocalDateTime.now());
        }
        return dewRepository.save(dew);
    }

    // NOT READY
    public DewForecast calculateForecast() {
        List<Dew> recentData = dewRepository.findTop10ByOrderByDateTimeDesc();

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

