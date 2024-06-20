package com.uos.electronic.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("Select u from User u where u.userEmail = :userEmail and u.userType.userTypeName = :userTypeName")
	Optional<User> findByUserEmailAndUserRole(String userEmail, String userTypeName);
	
	@Query("Select u from User u where u.userEmail = :userEmail")
	Optional<User> findByUserEmail(String userEmail);
	
	@Modifying
	@Transactional
	@Query(value = "delete from user where user_id = :userId", nativeQuery = true)
	void deleteRecordsByUserId(int userId);
	
}
