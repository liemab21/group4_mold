package com.co2.mold.controller;

import com.co2.mold.model.mold.Mold;
import com.co2.mold.service.MoldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mold/query")
@Tag(name = "Mold Data Queries", description = "Search, filter, and retrieve mold data using various criteria such as classroom, date ranges, and time periods")
public class MoldQueryController {

    private final MoldService moldService;

    public MoldQueryController(MoldService moldService) {
        this.moldService = moldService;
    }

    @GetMapping("/classroom/{classroom}")
    public ResponseEntity<List<Mold>> getByClassroom(@PathVariable String classroom) {
        return ResponseEntity.ok(moldService.getByClassroom(classroom));
    }

    @GetMapping("/latest/{classroom}")
    public ResponseEntity<Mold> getLatestByClassroom(@PathVariable String classroom) {
        Optional<Mold> mold = moldService.getLatestByClassroom(classroom);
        return mold.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-date-classroom")
    public ResponseEntity<List<Mold>> getByDateAndClassroom(
            @RequestParam LocalDateTime date,
            @RequestParam String classroom
    ) {
        return ResponseEntity.ok(moldService.getByDateAndClassroom(date, classroom));
    }

    @GetMapping("/time-range")
    public ResponseEntity<List<Mold>> getByTimeRange(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime
    ) {
        return ResponseEntity.ok(moldService.getByTimeRange(startTime, endTime));
    }

    @GetMapping("/classroom-time-range")
    public ResponseEntity<List<Mold>> getByClassroomAndTimeRange(
            @RequestParam String classroom,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime
    ) {
        return ResponseEntity.ok(moldService.getByClassroomAndTimeRange(classroom, startTime, endTime));
    }
}
