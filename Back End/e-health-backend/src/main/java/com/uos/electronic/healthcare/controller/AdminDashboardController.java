package com.uos.electronic.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.DoctorRegistrationBean;
import com.uos.electronic.healthcare.model.PracticeRegistrationBean;
import com.uos.electronic.healthcare.model.PractitionerRegistrationBean;
import com.uos.electronic.healthcare.model.UpdateUserPasswordBean;
import com.uos.electronic.healthcare.service.AdminDashboardService;

@RestController
public class AdminDashboardController {

	@Autowired
	private AdminDashboardService adminDashboardService;

	@GetMapping("/practice/registration/requests")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<PatientPracticeRegistration>> fetchPendingRegistrationRequests() {
		return new ResponseEntity<>(adminDashboardService.fetchPendingRegistrationRequests(), HttpStatus.OK);
	}

	@PutMapping("/practice/registration/requests/approve/{patient-practice-registration-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> approvePendingRegistrationRequests(
			@PathVariable(name = "patient-practice-registration-id") int patientPracticeRegistrationId)
			throws InvalidInputException {
		adminDashboardService.approvePendingRegistrationRequests(patientPracticeRegistrationId);
		return new ResponseEntity<>(Constants.REQUEST_APPROVED, HttpStatus.OK);
	}

	@PutMapping("/practice/registration/requests/decline/{patient-practice-registration-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> declinePendingRegistrationRequests(
			@PathVariable(name = "patient-practice-registration-id") int patientPracticeRegistrationId)
			throws InvalidInputException {
		adminDashboardService.declinePendingRegistrationRequests(patientPracticeRegistrationId);
		return new ResponseEntity<>(Constants.REQUEST_DECLINED, HttpStatus.OK);
	}

	@PostMapping(value = "/practices")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> addNewPractice(@RequestBody PracticeRegistrationBean practiceRegistrationBean) {
		adminDashboardService.addNewPractice(practiceRegistrationBean);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/users/doctors")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> addNewDoctor(@RequestBody DoctorRegistrationBean doctorRegistrationBean) {
		adminDashboardService.addNewDoctor(doctorRegistrationBean);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/new-practices")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<Practice>> fetchPracticesWithoutPractitioner() {
		return new ResponseEntity<>(adminDashboardService.fetchPracticesWithoutPractitioner(), HttpStatus.OK);
	}

	@PostMapping(value = "users/practitioners")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> addNewPractitioner(
			@RequestBody PractitionerRegistrationBean practitionerRegistrationBean) {
		adminDashboardService.addNewPractitioner(practitionerRegistrationBean);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/users/password")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> updateUserPassword(@RequestBody UpdateUserPasswordBean updateUserPasswordBean)
			throws InvalidInputException {
		adminDashboardService.updateUserPassword(updateUserPasswordBean);
		return new ResponseEntity<>("Password Updated", HttpStatus.OK);
	}

}
