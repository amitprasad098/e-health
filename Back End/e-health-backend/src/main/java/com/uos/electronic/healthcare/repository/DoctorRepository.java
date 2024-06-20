package com.uos.electronic.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	@Query("Select d from Doctor d where d.user.userId = :userId")
	Optional<Doctor> findByUserId(int userId);
	
	@Query("Select d from Doctor d where d.practice.practiceId = :practiceId")
	List<Doctor> findByPracticeId(int practiceId);

}
