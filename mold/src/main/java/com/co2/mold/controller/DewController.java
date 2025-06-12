package com.co2.mold.controller;

import com.co2.mold.pojos.DewData;
import com.co2.mold.pojos.DewForecast;
import com.co2.mold.repositories.DewRepository;
import com.co2.mold.services.DewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dew")
public class DewController {

    private final DewRepository dewRepository;
    private final DewService dewService;

    public DewController(DewRepository dewRepository, DewService dewService) {
        this.dewRepository = dewRepository;
        this.dewService = dewService;
    }

    @GetMapping("/dew")
    public ResponseEntity<List<DewData>> getAllDew() {
        List<DewData> dewList = dewRepository.findAll(); // das hab i nur zum testen gemacht kannst löschen wenn du net brauchst
        return ResponseEntity.ok(dewList);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<DewData>> getDewFromDate(@RequestParam LocalDate date) {
        List<DewData> dataFromDate = dewRepository.findByDate(date);
        return ResponseEntity.ok(dataFromDate);
    }

    @GetMapping("/forecast")
    public ResponseEntity<DewForecast> getForecast() {
        DewForecast forecast = dewService.calculateForecast();
        return ResponseEntity.ok(forecast);
    }

    @GetMapping("/calculate") // und an gescheiten pfad
    public ResponseEntity<Double> calculateDewPoint(
            @RequestParam double temperature, // mach du so wie wir die daten bekommen
            @RequestParam double humidity
    ) {
        double dewPoint = DewService.calculateDewPoint(temperature, humidity);
        return ResponseEntity.ok(dewPoint);
    }
}
