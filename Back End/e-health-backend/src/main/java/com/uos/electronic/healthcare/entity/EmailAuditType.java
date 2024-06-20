package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "email_audit_type")
@Data
public class EmailAuditType {
	
	@Id
	@Column(name = "email_audit_type_id")
	private int emailAuditTypeId;
	
	@Column(name = "email_audit_type_name")
	private String emailAuditTypeName;

}
