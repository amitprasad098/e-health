package com.uos.electronic.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.Pharmacy;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.RequestAlreadyExistException;
import com.uos.electronic.healthcare.model.PatientPracticeResponseBean;
import com.uos.electronic.healthcare.model.UserPracticeRegistrationBean;
import com.uos.electronic.healthcare.service.PatientPracticeRegistrationService;

@RestController
public class PatientPracticeRegistrationController {

	@Autowired
	private PatientPracticeRegistrationService patientPracticeRegistrationService;

	@GetMapping(value = "/practices")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<Practice>> fetchPractices() {
		List<Practice> practiceList = patientPracticeRegistrationService.fetchPractices();
		if (practiceList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(patientPracticeRegistrationService.fetchPractices(), HttpStatus.OK);
		}
	}

	@GetMapping(value = "/practices/{patient-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<PatientPracticeResponseBean>> fetchPracticesByPatientId(
			@PathVariable(name = "patient-id") int patientId) {
		List<Practice> practiceList = patientPracticeRegistrationService.fetchPractices();
		if (practiceList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(patientPracticeRegistrationService.fetchPracticesByPatientId(patientId),
					HttpStatus.OK);
		}
	}

	@PostMapping(value = "users/practice/registration")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> registerUserToPractice(
			@RequestBody UserPracticeRegistrationBean userPracticeRegistrationBean)
			throws RequestAlreadyExistException, InvalidInputException {
		patientPracticeRegistrationService.registerUserToPractice(userPracticeRegistrationBean);
		return new ResponseEntity<>("User Registration to Practice Request Raised", HttpStatus.CREATED);
	}

	@GetMapping(value = "users/practice/registration/status/{patient-id}/{practice-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> fetchRegistrationRequestStatus(@PathVariable(name = "patient-id") int patientId,
			@PathVariable(name = "practice-id") int practiceId) {
		return new ResponseEntity<>(
				patientPracticeRegistrationService.fetchRegistrationRequestStatus(patientId, practiceId),
				HttpStatus.OK);
	}

	@GetMapping(value = "/pharmacies/{city}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<Pharmacy>> fetchPharmaciesByCity(@PathVariable(name = "city") String city) {
		return new ResponseEntity<>(patientPracticeRegistrationService.fetchPharmaciesByCity(city), HttpStatus.OK);
	}

}
