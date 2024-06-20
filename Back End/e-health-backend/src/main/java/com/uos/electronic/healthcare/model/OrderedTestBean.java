package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderedTestBean {

	private int appointmentBookingId;
	private String orderedTestDetails;
	private String orderedTestDate;

}
