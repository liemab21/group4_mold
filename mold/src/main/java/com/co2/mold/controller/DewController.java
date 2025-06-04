package com.co2.mold.controller;

import com.co2.mold.repositories.DewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class DewController {

    private final DewRepository dewRepository;

    public DewController(DewRepository dewRepository) {
        this.dewRepository = dewRepository;
    }

    @GetMapping("/dew")
    public ResponseEntity<?> getDew() {
        return null;
    }

    @GetMapping("/dew")
    public ResponseEntity<?> getDewWithClass(@RequestParam String className) {
        return null;
    }

    @GetMapping("/dewWithDate")
    public ResponseEntity<?> getDewFromDate(
            @RequestParam LocalDate date
            )
    {
        return null;
    }

    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast()
    {
        return null;
    }



}
