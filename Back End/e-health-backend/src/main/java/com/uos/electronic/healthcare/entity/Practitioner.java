package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "practitioner")
@Data
public class Practitioner {
	
	@Id
	@Column(name = "practitioner_id")
	private int practitionerId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "practice_id", nullable = false)
	private Practice practice;

	@Column(name = "practitioner_degree")
	private String practitionerDegree;

}
