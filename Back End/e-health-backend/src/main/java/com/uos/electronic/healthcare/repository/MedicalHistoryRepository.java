package com.uos.electronic.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.MedicalHistory;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

	@Query("Select mh from MedicalHistory mh where mh.appointmentBooking.patientPracticeRegistration.patientPracticeRegistrationId = :patientPracticeRegistrationId")
	List<MedicalHistory> findByPatientPracticeRegistraionId(int patientPracticeRegistrationId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from medical_history where appointment_booking_id in\n"
			+ "(select appointment_booking_id from appointment_booking where patient_practice_registration_id in \n"
			+ "(select patient_practice_registration_id  from patient_practice_registration where patient_id in \n"
			+ "(select patient_id from patient where user_id = :userId)))", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
