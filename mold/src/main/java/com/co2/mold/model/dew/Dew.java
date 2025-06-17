package com.co2.mold.model.dew;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Dew {

    @Id
    @GeneratedValue
    private Long id;

    private double temperature;
    private double humidity;
    private double dewPoint;
    private LocalDateTime datetime;
}