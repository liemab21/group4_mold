package com.co2.mold.repository;

import com.co2.mold.model.dew.Dew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DewRepository extends JpaRepository<Dew, Long> {
    List<Dew> findByDatetime(LocalDateTime datetime);
    List<Dew> findTop10ByOrderByDateTimeDesc();
}