package com.uos.electronic.healthcare.service;

import com.uos.electronic.healthcare.entity.Prescription;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientPrescriptionBean;

public interface PrescriptionService {

	Prescription fetchPrescriptionDetails(int appointmentBookingId);

	Prescription uploadPatientPrescription(PatientPrescriptionBean patientPrescriptionBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException;

}
