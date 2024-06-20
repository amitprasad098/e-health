package com.uos.electronic.healthcare.service;

import com.uos.electronic.healthcare.entity.SpecialistReferral;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.SpecialistReferralBean;

public interface SpecialistReferralService {

	SpecialistReferral fetchSpecialistReferralDetails(int appointmentBookingId);

	SpecialistReferral uploadSpecialistReferral(SpecialistReferralBean specialistReferralBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException;

}
