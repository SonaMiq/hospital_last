package com.example.hospital_management_system.service;

import com.example.hospital_management_system.domain.entity.Doctor;
import com.example.hospital_management_system.domain.entity.DoctorAppointment;
import com.example.hospital_management_system.domain.entity.Registration;
import com.example.hospital_management_system.domain.entity.WorkGraphic;
import com.example.hospital_management_system.domain.enums.AppointmentStatus;
import com.example.hospital_management_system.repository.DoctorAppointmentRepository;
import com.example.hospital_management_system.repository.DoctorRepository;
import com.example.hospital_management_system.repository.RegistrationRepository;
import com.example.hospital_management_system.repository.WorkGraphicRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DoctorAppointmentService {
    private final RegistrationRepository registrationRepository;
    private final DoctorAppointmentRepository doctorAppointmentRepository;
    private final WorkGraphicRepository workGraphicRepository;
    private final DoctorRepository doctorRepository;

    public DoctorAppointmentService(RegistrationRepository registrationRepository,DoctorAppointmentRepository doctorAppointmentRepository,
                                    WorkGraphicRepository workGraphicRepository,DoctorRepository doctorRepository) {
        this.registrationRepository = registrationRepository;
        this.doctorAppointmentRepository = doctorAppointmentRepository;
        this.workGraphicRepository=workGraphicRepository;
        this.doctorRepository=doctorRepository;
    }
    public List<DoctorAppointment> getFreeTimes(Long doctorId, Date date) {
        Registration registration = registrationRepository.findRegistrationByDoctorIdAndRegDay(doctorId, date);
        if (registration == null) {
           WorkGraphic workGraphic=workGraphicRepository.findByDoctorIdAndWeekDay(doctorId,date.toLocalDate().getDayOfWeek());
            Doctor doctor=doctorRepository.findById(doctorId).get();
            int time = workGraphic.getStart();
            int count=1;
            while (time < workGraphic.getEnd()) {

                DoctorAppointment doctorAppointment = new DoctorAppointment();
                doctorAppointment.setDoctor(doctor);
                doctorAppointment.setDate(date);
                if(count%2!=0) {
                    doctorAppointment.setStartTime(time + ":00");
                    doctorAppointment.setEndTime(time + ":30");
                }
                if(count%2==0) {
                    doctorAppointment.setStartTime(time + ":30");
                    doctorAppointment.setEndTime(time + 1 + ":00");
                    time++;
                }
                count++;
                doctorAppointmentRepository.save(doctorAppointment);

            }
        }
        return doctorAppointmentRepository.findDoctorAppointmentByDoctorIdAndDateAndAppointmentStatus
                (doctorId, date, AppointmentStatus.FREE);

    }
}







