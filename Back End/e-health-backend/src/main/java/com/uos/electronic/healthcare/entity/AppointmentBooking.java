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
@Table(name = "appointment_booking")
@Data
public class AppointmentBooking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_booking_id")
	private int appointmentBookingId;
	
	@ManyToOne
	@JoinColumn(name = "patient_practice_registration_id", nullable = false)
	private PatientPracticeRegistration patientPracticeRegistration;
	
	@ManyToOne
	@JoinColumn(name = "practitioner_id", nullable = false)
	private Practitioner practitioner;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@Column(name = "appointment_details")
	private String appointmentDetails;
	
	@Column(name = "appointment_status")
	private String appointmentStatus;
	
	@Column(name = "practitioner_feedback")
	private String practitionerFeedback;

	@Column(name = "appointment_date")
	private Date appointmentDate;
	
	@ManyToOne
	@JoinColumn(name = "referred_doctor_id")
	private Doctor referredDoctor;

}
