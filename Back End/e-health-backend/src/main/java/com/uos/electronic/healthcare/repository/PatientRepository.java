package com.uos.electronic.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	@Query("Select p from Patient p where p.user.userId = :userId")
	Optional<Patient> findByUserId(int userId);
	
	@Query("Select p from Patient p where p.user.userEmail = :userEmail")
	Optional<Patient> findByUserEmail(String userEmail);
	
	@Query("Select p from Patient p where p.patientId = :patientId")
	Optional<Patient> findByPatientId(int patientId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from patient where user_id = :userId", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
