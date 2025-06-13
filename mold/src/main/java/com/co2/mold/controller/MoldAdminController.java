package com.co2.mold.controller;

import com.co2.mold.model.Interval;
import com.co2.mold.model.mold.MoldRisk;
import com.co2.mold.service.MoldRiskService;
import com.co2.mold.service.MoldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/mold/admin")
@Tag(name = "Mold Administration", description = "Administrative operations for mold data management including statistics, bulk operations, and data cleanup")
public class MoldAdminController {

    private final MoldService moldService;
    private final MoldRiskService moldRiskService;

    public MoldAdminController(MoldService moldService, MoldRiskService moldRiskService) {
        this.moldService = moldService;
        this.moldRiskService = moldRiskService;
    }

    @GetMapping("/count-by-risk")
    public ResponseEntity<Long> countByRiskLevel(@RequestParam MoldRisk riskLevel) {
        return ResponseEntity.ok(moldService.countByRiskLevel(riskLevel));
    }

    @DeleteMapping("/classroom/{classroom}")
    public ResponseEntity<Long> deleteByClassroom(@PathVariable String classroom) {
        long deletedCount = moldService.deleteByClassroom(classroom);
        return ResponseEntity.ok(deletedCount);
    }

    @DeleteMapping("/older-than")
    public ResponseEntity<Long> deleteOlderThan(@RequestParam LocalDateTime cutoffDate) {
        long deletedCount = moldService.deleteOlderThan(cutoffDate);
        return ResponseEntity.ok(deletedCount);
    }


    @PostMapping("/interval")
    private ResponseEntity<?> setFetchInterval(
            @RequestBody Interval interval
    ) {
        return ResponseEntity.ok(moldRiskService.setFetchInterval(interval));
    }
}