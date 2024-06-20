package com.uos.electronic.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegistrationBean {
	
	private String doctorName;
	private String doctorEmail;
	private String doctorPassword;
	private String practiceName;
	private String doctorDegree;
	private String doctorSpeciality;

}
