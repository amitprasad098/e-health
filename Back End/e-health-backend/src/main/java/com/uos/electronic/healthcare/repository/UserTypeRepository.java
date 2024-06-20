package com.uos.electronic.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
	
	@Query("Select ut from UserType ut where ut.userTypeName = :userTypeName")
	Optional<UserType> findByUserRole(String userTypeName);

}
