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

import com.uos.electronic.healthcare.entity.SpecialistReferral;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.SpecialistReferralBean;
import com.uos.electronic.healthcare.service.SpecialistReferralService;

@RestController
@RequestMapping(value = "/patients/referral")
public class SpecialistReferralController {

	@Autowired
	private SpecialistReferralService specialistReferralService;

	@GetMapping(value = "/{appointment-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<SpecialistReferral> fetchSpecialistReferralDetails(
			@PathVariable(name = "appointment-id") int appointmentBookingId) {
		SpecialistReferral specialistReferral = specialistReferralService
				.fetchSpecialistReferralDetails(appointmentBookingId);
		if (Objects.isNull(specialistReferral)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(specialistReferral, HttpStatus.OK);
	}

	@PostMapping
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<SpecialistReferral> uploadSpecialistReferral(
			@RequestBody SpecialistReferralBean specialistReferralBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		return new ResponseEntity<>(specialistReferralService.uploadSpecialistReferral(specialistReferralBean),
				HttpStatus.CREATED);
	}

}