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
@Table(name = "ordered_test")
@Data
public class OrderedTest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ordered_test_id")
	private int orderedTestId;
	
	@ManyToOne
	@JoinColumn(name = "appointment_booking_id", nullable = false)
	private AppointmentBooking appointmentBooking;
	
	@Column(name = "ordered_test_details")
	private String orderedTestDetails;
	
	@Column(name = "ordered_test_date")
	private Date orderedTestDate;

}
