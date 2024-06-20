package com.uos.electronic.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientDataBean {

	private int userId;
	private String patientName;
	private String patientEmail;
	private String patientContactNumber;
	private String patientAddress;
	private String patientPassword;

}
