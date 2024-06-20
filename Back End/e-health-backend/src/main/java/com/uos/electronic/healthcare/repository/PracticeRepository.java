package com.uos.electronic.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.Practice;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {
	
	@Query("Select p from Practice p where p.practiceName = :practiceName")
	Optional<Practice> findByPracticeName(String practiceName);

}
