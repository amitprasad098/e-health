package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "doctor")
@Data
public class Doctor {

	@Id
	@Column(name = "doctor_id")
	private int doctorId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "practice_id", nullable = false)
	private Practice practice;

	@Column(name = "doctor_degree")
	private String doctorDegree;

	@Column(name = "doctor_speciality")
	private String doctorSpeciality;

}
