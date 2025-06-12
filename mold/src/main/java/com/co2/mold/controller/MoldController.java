package com.co2.mold.controller;

import com.co2.mold.model.Interval;
import com.co2.mold.service.MoldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mold")
public class MoldController {

    final MoldService moldService;

    public MoldController(MoldService moldService) {
        this.moldService = moldService;
    }

    @GetMapping("/")
    private ResponseEntity<?> getCurrentMoldStatus(
            @RequestParam String classroom,
            @RequestParam String time
    ){
        return ResponseEntity.status(HttpStatus.OK).body(moldService.getCurrentMoldStatus());
    }

    @GetMapping("/forecast")
    private ResponseEntity<?> getCurrentMoldForecast() {
        return ResponseEntity.status(HttpStatus.OK).body(moldService.getCurrentMoldForecast());

    }

    @PostMapping("/intervall")
    private ResponseEntity<?> setFetchIntervall(
            @RequestBody Interval interval
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(moldService.setFetchInterval(interval));
    }
}
