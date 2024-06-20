package com.uos.electronic.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;

@Repository
public interface PatientPracticeRegistrationRepository extends JpaRepository<PatientPracticeRegistration, Integer> {

	@Query("Select ppr from PatientPracticeRegistration ppr where ppr.registrationStatus = :registrationStatus")
	List<PatientPracticeRegistration> findByRegistrationStatus(String registrationStatus);

	@Query("Select ppr from PatientPracticeRegistration ppr where ppr.patient.patientId = :patientId and ppr.practice.practiceId = :practiceId")
	Optional<PatientPracticeRegistration> findByPatientIdAndPracticeId(int patientId, int practiceId);

	@Modifying
	@Transactional
	@Query(value = "delete from patient_practice_registration where patient_id in (select patient_id from patient where user_id = :userId)", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
