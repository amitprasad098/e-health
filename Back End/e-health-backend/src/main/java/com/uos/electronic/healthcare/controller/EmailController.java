package com.uos.electronic.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendMail")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> sendMail(@RequestBody EmailDetails details) {
		return new ResponseEntity<>(emailService.sendSimpleMail(details), HttpStatus.CREATED);
	}

	@PostMapping("/sendMailWithAttachment")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> sendMailWithAttachment(@RequestBody EmailDetails details) {
		return new ResponseEntity<>(emailService.sendMailWithAttachment(details), HttpStatus.CREATED);
	}
}
