package com.uos.electronic.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.electronic.healthcare.entity.EmailAuditType;

@Repository
public interface EmailAuditTypeRepository extends JpaRepository<EmailAuditType, Integer> {
	
	@Query("Select eat from EmailAuditType eat where eat.emailAuditTypeName = :emailAuditTypeName")
	Optional<EmailAuditType> findByEmailAuditTypeName(String emailAuditTypeName);

}
