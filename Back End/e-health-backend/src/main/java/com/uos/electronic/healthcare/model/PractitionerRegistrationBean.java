package com.uos.electronic.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerRegistrationBean {
	
	private String practitionerName;
	private String practitionerEmail;
	private String practitionerPassword;
	private String practiceName;
	private String practitionerDegree;

}
