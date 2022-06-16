package com.example.hospital_management_system.repository;

import com.example.hospital_management_system.domain.entity.WorkGraphic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;

@Repository
public interface WorkGraphicRepository extends JpaRepository<WorkGraphic,Integer> {
 WorkGraphic findByDoctorIdAndWeekDay(Long doctorId, DayOfWeek weekDay);
}
