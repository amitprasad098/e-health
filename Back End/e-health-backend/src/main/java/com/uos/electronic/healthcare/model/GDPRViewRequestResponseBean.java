package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GDPRViewRequestResponseBean {
	
	private String patientName;
	private String patientEmail;
	private String patientAddress;
	private String patientDateOfBirth;
	private String patientGender;
	private String patientContactNumber;

}
