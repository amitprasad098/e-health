package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.MedicalHistory;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.MedicalHistoryBean;

public interface MedicalHistoryService {

	List<MedicalHistory> fetchMedicalHistoryDetails(int appointmentBookingId) throws InvalidInputException;

	MedicalHistory uploadMedicalHistory(MedicalHistoryBean medicalHistoryBean)
			throws InvalidInputException, DateExtractionException;

}
