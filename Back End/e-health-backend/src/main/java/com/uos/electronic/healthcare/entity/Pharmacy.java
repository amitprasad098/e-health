package com.uos.electronic.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "pharmacy")
@Data
public class Pharmacy {
	
	@Id
	@Column(name = "pharmacy_id")
	private int pharmacyId;
	
	@Column(name = "pharmacy_opening_times")
	private String pharmacyOpeningTimes;
	
	@Column(name = "pharmacy_name")
	private String pharmacyName;
	
	@Column(name = "pharmacy_address")
	private String pharmacyAddress;
	
	@Column(name = "pharmacy_contact_number")
	private String pharmacyContactNumber;
	
	@Column(name = "pharmacy_map_url")
	private String pharmacyMapUrl;
	
	@Column(name = "pharmacy_city")
	private String pharmacyCity;

}
