package com.uos.electronic.healthcare.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.Prescription;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientPrescriptionBean;
import com.uos.electronic.healthcare.service.PrescriptionService;

@RestController
@RequestMapping(value = "/patients/prescription")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;

	@GetMapping(value = "/{appointment-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<Prescription> fetchPrescriptionDetails(
			@PathVariable(name = "appointment-id") int appointmentBookingId) {
		Prescription prescription = prescriptionService.fetchPrescriptionDetails(appointmentBookingId);
		if (Objects.isNull(prescription)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(prescription, HttpStatus.OK);
	}

	@PostMapping()
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<Prescription> uploadPatientPrescription(
			@RequestBody PatientPrescriptionBean patientPrescriptionBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		return new ResponseEntity<>(prescriptionService.uploadPatientPrescription(patientPrescriptionBean),
				HttpStatus.CREATED);
	}

}
