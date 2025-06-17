package com.co2.mold.controller;
import com.co2.mold.model.IntervalRequest;

import com.co2.mold.service.PollingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interval")
public class IntervalController {

    private final PollingService pollingService;

    public IntervalController(PollingService pollingService) {
        this.pollingService = pollingService;
    }

    @PostMapping
    public ResponseEntity<String> updateInterval(@RequestBody IntervalRequest request) {
        if (request.getMs() < 1000) {
            return ResponseEntity.badRequest().body("Minimum interval is 1000 ms (1 second).");
        }

        pollingService.updateInterval(request.getMs());
        return ResponseEntity.ok("Interval updated to " + request.getMs() + " ms");
    }
}