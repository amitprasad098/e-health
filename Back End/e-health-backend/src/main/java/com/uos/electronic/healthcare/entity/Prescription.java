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
@Table(name = "prescription")
@Data
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prescription_id")
	private int prescriptionId;

	@ManyToOne
	@JoinColumn(name = "appointment_booking_id", nullable = false)
	private AppointmentBooking appointmentBooking;

	@Column(name = "prescription_details")
	private String prescriptionDetails;

	@Column(name = "prescription_date")
	private Date prescriptionDate;

}
