package com.uos.electronic.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeRegistrationBean {
	
	private String practiceName;
	private String practiceDescription;
	private String practiceAddress;
	private String practiceContactDetails;
	private String practiceImageUrl;

}
