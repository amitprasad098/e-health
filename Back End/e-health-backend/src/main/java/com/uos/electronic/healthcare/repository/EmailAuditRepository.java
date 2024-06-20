package com.uos.electronic.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.electronic.healthcare.entity.EmailAudit;

@Repository
public interface EmailAuditRepository extends JpaRepository<EmailAudit, Integer> {
	
	@Modifying
    @Transactional
	@Query(value = "delete from email_audit where email_recipient_id in (select user_id from user where user_id = :userId)", nativeQuery = true)
	void deleteRecordsByUserId(int userId);

}
