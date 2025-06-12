package com.co2.mold.controller;

import com.co2.mold.model.Interval;
import com.co2.mold.service.MoldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/mold")
public class MoldController {

    final MoldService moldService;

    public MoldController(MoldService moldService) {
        this.moldService = moldService;
    }

    @GetMapping("/status")
    private ResponseEntity<?> getCurrentMoldStatus(
            @RequestParam String classroom,
            @RequestParam LocalDateTime time
    ){
        return ResponseEntity.status(HttpStatus.OK).body(moldService.getCurrentMoldStatus(time, classroom));
    }

    @GetMapping("/forecast")
    private ResponseEntity<?> getCurrentMoldForecast(
            @RequestParam String classroom
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(moldService.getCurrentMoldForecast(classroom));

    }

    @PostMapping("/interval")
    private ResponseEntity<?> setFetchInterval(
            @RequestBody Interval interval
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(moldService.setFetchInterval(interval));
    }
}
