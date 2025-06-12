package com.co2.mold.service;

import com.co2.mold.model.Interval;
import com.co2.mold.model.mold.Mold;
import com.co2.mold.model.mold.MoldRisk;
import com.co2.mold.repository.MoldRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MoldService {

    private final MoldRepository moldRepository;

    public MoldService(MoldRepository moldRepository) {
        this.moldRepository = moldRepository;
    }

    // Get current mold status for specific time and classroom
    public List<MoldRisk> getCurrentMoldStatus(LocalDateTime time, String classroom) {
        // Find mold data within 1 hour range of the specified time
        LocalDateTime startTime = time.minusHours(1);
        LocalDateTime endTime = time.plusHours(1);
        List<Mold> moldData = moldRepository.findByClassroomAndDatetimeBetween(
                classroom, startTime, endTime
        );

        return moldData.stream()
                .map(Mold::getMoldRisk)
                .toList();
    }

    // Get all mold data
    public List<Mold> getAllMoldData() {
        return moldRepository.findAll();
    }

    // Get mold data by date and classroom
    public List<Mold> getByDateAndClassroom(LocalDateTime date, String classroom) {
        // Get data for the entire day
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);

        return moldRepository.findByClassroomAndDatetimeBetween(
                classroom, startOfDay, endOfDay
        );
    }

    // Get mold data by classroom only
    public List<Mold> getByClassroom(String classroom) {
        return moldRepository.findByClassroom(classroom);
    }

    // Insert new mold data
    public Mold insertMoldData(Mold mold) {
        // Set datetime to now if not provided
        if (mold.getDatetime() == null) {
            mold.setDatetime(LocalDateTime.now());
        }

        return moldRepository.save(mold);
    }

    // Insert new mold data with parameters
    public Mold insertMoldData(String classroom, MoldRisk moldRisk) {
        Mold mold = new Mold();
        mold.setClassroom(classroom);
        mold.setMoldRisk(moldRisk);
        mold.setDatetime(LocalDateTime.now());

        return moldRepository.save(mold);
    }

    // Delete mold data by ID
    public boolean deleteById(Long id) {
        if (moldRepository.existsById(id)) {
            moldRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get mold data by ID
    public Optional<Mold> getById(Long id) {
        return moldRepository.findById(id);
    }

    // Get latest mold data for a classroom
    public Optional<Mold> getLatestByClassroom(String classroom) {
        Mold latest = moldRepository.findTopByClassroomOrderByDatetimeDesc(classroom);
        return Optional.ofNullable(latest);
    }

    // Get mold data within time range
    public List<Mold> getByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return moldRepository.findByDatetimeBetween(startTime, endTime);
    }

    // Get mold data by classroom within time range
    public List<Mold> getByClassroomAndTimeRange(String classroom, LocalDateTime startTime, LocalDateTime endTime) {
        return moldRepository.findByClassroomAndDatetimeBetween(classroom, startTime, endTime);
    }

    // Count mold entries by risk level
    public long countByRiskLevel(MoldRisk riskLevel) {
        return moldRepository.countByMoldRisk(riskLevel);
    }

    // Delete all mold data for a classroom
    public long deleteByClassroom(String classroom) {
        return moldRepository.deleteByClassroom(classroom);
    }

    // Delete mold data older than specified date
    public long deleteOlderThan(LocalDateTime cutoffDate) {
        return moldRepository.deleteByDatetimeBefore(cutoffDate);
    }

    // NOT READY FOR IMPLEMENTATION
    public MoldRisk getCurrentMoldForecast(String classroom) {
        // Placeholder for future forecast logic
        // Could use ML models, trend analysis, etc.
        return null;
    }

    // NOT READY FOR IMPLEMENTATION
    public Interval setFetchInterval(Interval interval) {
        // Placeholder for interval configuration
        // Could save to database or update application properties
        return null;
    }
}
