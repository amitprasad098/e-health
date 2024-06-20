package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "admin")
@Data
public class Admin {

	@Id
	@Column(name = "admin_id")
	private int adminId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
