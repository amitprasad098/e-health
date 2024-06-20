package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalHistoryBean {

	private int appointmentBookingId;
	private String medicalHistoryDetails;
	private String medicalHistoryDate;
	
}
