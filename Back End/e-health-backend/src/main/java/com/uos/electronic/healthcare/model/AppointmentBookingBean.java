package com.uos.electronic.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentBookingBean {
	
	private int patientId;
	private int practiceId;
	private String appointmentDescription;
	private String appointmentDate;
	
}
