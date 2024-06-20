package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "email_audit")
@Data
public class EmailAudit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_audit_id")
	private int emailAuditId;

	@ManyToOne
	@JoinColumn(name = "email_audit_type_id", nullable = false)
	private EmailAuditType emailAuditType;
	
	@ManyToOne
	@JoinColumn(name = "email_sender_id", nullable = false)
	private User emailSender;
	
	@ManyToOne
	@JoinColumn(name = "email_recipient_id", nullable = false)
	private User emailReceiver;

	@Column(name = "email_subject")
	private String emailSubject;
	
	@Column(name = "email_message")
	private String emailMessage;

}
