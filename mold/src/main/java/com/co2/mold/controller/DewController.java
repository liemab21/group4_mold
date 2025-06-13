package com.co2.mold.controller;

import com.co2.mold.model.dew.Dew;
import com.co2.mold.model.dew.DewForecast;
import com.co2.mold.service.DewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dew")
@Tag(name = "Dew Controller", description = "Dew operations")
public class DewController {

    private final DewService dewService;

    public DewController(DewService dewService) {
        this.dewService = dewService;
    }

    @GetMapping
    public ResponseEntity<List<Dew>> getAllDew() {
        List<Dew> dewList = dewService.getAllDewData();
        return ResponseEntity.ok(dewList);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<Dew>> getDewFromDate(@RequestParam LocalDate date) {
        List<Dew> dataFromDate = dewService.getDewDataByDate(date);
        return ResponseEntity.ok(dataFromDate);
    }

    @GetMapping("/forecast")
    public ResponseEntity<DewForecast> getForecast() {
        DewForecast forecast = dewService.calculateForecast();
        return ResponseEntity.ok(forecast);
    }

    @GetMapping("/calculate-dewpoint")
    public ResponseEntity<Double> calculateDewPoint(
            @RequestParam double temperature,
            @RequestParam double humidity
    ) {
        double dewPoint = DewService.calculateDewPoint(temperature, humidity);
        return ResponseEntity.ok(dewPoint);
    }
}