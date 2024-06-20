package com.uos.electronic.healthcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.Pharmacy;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
	
	@Query("Select p from Pharmacy p where p.pharmacyCity = :city")
	List<Pharmacy> findByCity(String city);

}
