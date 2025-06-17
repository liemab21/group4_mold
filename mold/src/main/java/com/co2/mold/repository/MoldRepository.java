package com.co2.mold.repository;

import com.co2.mold.model.mold.Mold;
import com.co2.mold.model.mold.MoldRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MoldRepository extends JpaRepository<Mold, Long> {
    List<Mold> findByClassroomAndDatetimeBetween(String classroom, LocalDateTime datetimeAfter, LocalDateTime datetimeBefore);

    List<Mold> findByClassroom(String classroom);

    Mold findTopByClassroomOrderByDatetimeDesc(String classroom);

    List<Mold> findByDatetimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    long countByMoldRisk(MoldRisk moldRisk);

    long deleteByClassroom(String classroom);

    long deleteByDatetimeBefore(LocalDateTime cutoffDate);

    List<Mold> findTop10ByOrderByDatetimeDesc();
}