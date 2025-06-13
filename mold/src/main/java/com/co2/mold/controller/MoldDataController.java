package com.co2.mold.controller;

import com.co2.mold.model.mold.Mold;
import com.co2.mold.service.MoldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mold/data")
@Tag(name = "Mold Data Management", description = "CRUD operations for creating, reading, updating, and deleting mold data records")
public class MoldDataController {

    private final MoldService moldService;

    public MoldDataController(MoldService moldService) {
        this.moldService = moldService;
    }

    @GetMapping
    public ResponseEntity<List<Mold>> getAllMoldData() {
        return ResponseEntity.ok(moldService.getAllMoldData());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Mold>> getFilteredData(
            @RequestParam LocalDateTime time,
            @RequestParam String classRoom
    ) {
        return ResponseEntity.ok(moldService.getByDateAndClassroom(time, classRoom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mold> getById(@PathVariable Long id) {
        Optional<Mold> mold = moldService.getById(id);
        return mold.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mold> insertMoldData(@RequestBody Mold mold) {
        Mold savedMold = moldService.insertMoldData(mold);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMold);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = moldService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}