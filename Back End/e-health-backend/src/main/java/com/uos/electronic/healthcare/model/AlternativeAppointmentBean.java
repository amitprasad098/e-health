package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlternativeAppointmentBean {
	private int appointmentBookingId;
	private String alternativeMessage;

}
