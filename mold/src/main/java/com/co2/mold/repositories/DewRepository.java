package com.co2.mold.repositories;

import com.co2.mold.pojos.DewData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DewRepository extends JpaRepository<DewData, Long> {
    List<DewData> findByDate(LocalDate date);
    List<DewData> findTop10ByOrderByDateDesc();
}
