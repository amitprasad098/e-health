package com.uos.electronic.healthcare.controller;

import java.util.List;
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

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.Patient;
import com.uos.electronic.healthcare.entity.UserType;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientRegistrationBean;
import com.uos.electronic.healthcare.model.UserSignInBean;
import com.uos.electronic.healthcare.service.UserManagementService;

@RestController
@RequestMapping(value = "/users")
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;

	@GetMapping(value = "/types")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<UserType>> fetchUserTypes() {
		return new ResponseEntity<>(userManagementService.fetchUserTypes(), HttpStatus.OK);
	}

	@PostMapping
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<Patient> registerPatient(@RequestBody PatientRegistrationBean patientRegistrationBean)
			throws DateExtractionException {
		return new ResponseEntity<>(userManagementService.registerPatient(patientRegistrationBean), HttpStatus.CREATED);
	}

	@PostMapping(value = "/sign-in")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<Object> signInUser(@RequestBody UserSignInBean userSignInBean) throws InvalidInputException {
		Object signedInUser = userManagementService.signInUser(userSignInBean);
		if (!Objects.isNull(signedInUser)) {
			return new ResponseEntity<>(signedInUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Sign In Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/forgot-password/{user-email}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> forgotPasswordManagement(@PathVariable(name = "user-email") String userEmail)
			throws InvalidInputException {
		userManagementService.forgotPasswordManagement(userEmail);
		return new ResponseEntity<>("Email sent successfully", HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/logout")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> logoutUser() {
		return new ResponseEntity<>(Constants.LOGOUT_SUCCESSFUL, HttpStatus.OK);
	}
	
}
