package com.uos.electronic.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.Practitioner;

@Repository
public interface PractitionerRepository extends JpaRepository<Practitioner, Integer> {
	
	@Query("Select p from Practitioner p where p.user.userId = :userId")
	Optional<Practitioner> findByUserId(int userId);
	
	@Query("Select p from Practitioner p where p.practice.practiceId = :practiceId")
	List<Practitioner> findByPracticeId(int practiceId);

}
