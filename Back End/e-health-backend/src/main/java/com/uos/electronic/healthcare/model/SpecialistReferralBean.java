package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistReferralBean {
	
	private int appointmentBookingId;
	private int referredDoctorId;
	private String specialistReferralDetails;
	private String specialistReferralDate;

}
