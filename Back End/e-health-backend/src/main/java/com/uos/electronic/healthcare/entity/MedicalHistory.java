package com.uos.electronic.healthcare.entity;

import java.util.Date;

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
@Table(name = "medical_history")
@Data
public class MedicalHistory {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medical_history_id")
	private int medicalHistoryId;
	
	@ManyToOne
	@JoinColumn(name = "appointment_booking_id", nullable = false)
	private AppointmentBooking appointmentBooking;
	
	@Column(name = "medical_history_details")
	private String medicalHistoryDetails;
	
	@Column(name = "medical_history_date")
	private Date medicalHistoryDate;

}
