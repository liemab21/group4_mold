package com.co2.mold.repository;

import com.co2.mold.model.dew.Dew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DewRepository extends JpaRepository<Dew, Long> {
    List<Dew> findByDate(LocalDate date);
    List<Dew> findTop10ByOrderByDateDesc();
}
