package com.co2.mold.model.mold;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Mold {
    @Id
    @GeneratedValue
    private Long id;

    String classroom;
    MoldRisk moldRisk;
    private LocalDateTime datetime;
}