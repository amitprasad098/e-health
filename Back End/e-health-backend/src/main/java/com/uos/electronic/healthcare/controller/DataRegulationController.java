package com.uos.electronic.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.UpdatePatientDataBean;
import com.uos.electronic.healthcare.service.DataRegulationService;

@RestController
@RequestMapping(value = "/users/data")
public class DataRegulationController {

	@Autowired
	private DataRegulationService dataRegulationService;

	@GetMapping(value = "{user-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> viewUserData(@PathVariable(name = "user-id") int userId)
			throws InvalidInputException {
		dataRegulationService.viewUserData(userId);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@PostMapping
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> updateUserData(@RequestBody UpdatePatientDataBean updatePatientDataBean)
			throws InvalidInputException {
		dataRegulationService.updateUserData(updatePatientDataBean);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@DeleteMapping(value = "{user-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> deleteUserData(@PathVariable(name = "user-id") int userId)
			throws InvalidInputException {
		dataRegulationService.deleteUserData(userId);
		return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
	}

}
