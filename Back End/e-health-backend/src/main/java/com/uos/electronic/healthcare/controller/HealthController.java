package com.uos.electronic.healthcare.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class HealthController {

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succesfully retrieved"),
			@ApiResponse(code = 404, message = "Page could not be found")
	})
	@ApiOperation(value = "Application Health Check API")
	@GetMapping(value = "/health")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Up", HttpStatus.OK);
	}
	
}
