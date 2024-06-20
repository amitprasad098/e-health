package com.uos.electronic.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.AppointmentBooking;

@Repository
public interface AppointmentBookingRepository extends JpaRepository<AppointmentBooking, Integer> {

	@Query("Select ab from AppointmentBooking ab where ab.appointmentStatus = :appointmentStatus")
	List<AppointmentBooking> findByAppointmentStatus(String appointmentStatus);

	@Query("Select ab from AppointmentBooking ab where ab.patientPracticeRegistration.patient.patientId = :patientId")
	List<AppointmentBooking> findByPatientId(int patientId);
	
	@Query("Select ab from AppointmentBooking ab where ab.doctor.doctorId = :doctorId")
	List<AppointmentBooking> findByDoctorId(int doctorId);

	@Query("Select ab from AppointmentBooking ab where ab.practitioner.practitionerId = :practitionerId and ab.appointmentStatus = :appointmentStatus")
	List<AppointmentBooking> findByPractitionerIdAndAppointmentStatus(int practitionerId, String appointmentStatus);
	
	@Modifying
	@Transactional
	@Query(value = "delete from appointment_booking where patient_practice_registration_id in \n"
			+ "(select patient_practice_registration_id  from patient_practice_registration where patient_id in \n"
			+ "(select patient_id from patient where user_id = :userId))", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
