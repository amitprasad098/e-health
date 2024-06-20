package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultBean {
	
	private int appointmentBookingId;
	private String testResult;

}
