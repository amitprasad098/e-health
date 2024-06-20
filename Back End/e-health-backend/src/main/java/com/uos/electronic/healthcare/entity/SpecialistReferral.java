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
@Table(name = "specialist_referral")
@Data
public class SpecialistReferral {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specialist_referral_id")
	private int specialistReferralId;
	
	@ManyToOne
	@JoinColumn(name = "appointment_booking_id", nullable = false)
	private AppointmentBooking appointmentBooking;
	
	@ManyToOne
	@JoinColumn(name = "referred_doctor_id", nullable = false)
	private Doctor referredDoctor;
	
	@Column(name = "specialist_referral_details")
	private String specialistReferralDetails;
	
	@Column(name = "specialist_referral_date")
	private Date specialistReferralDate;

}
