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

import com.uos.electronic.healthcare.entity.EmailAudit;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.OrderedTestBean;
import com.uos.electronic.healthcare.model.TestResultBean;
import com.uos.electronic.healthcare.service.OrderedTestService;

@RestController
@RequestMapping(value = "/patients/tests")
public class OrderedTestController {

	@Autowired
	private OrderedTestService orderedTestService;

	@GetMapping(value = "/{appointment-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<OrderedTest> fetchOrderedTestDetails(
			@PathVariable(name = "appointment-id") int appointmentBookingId) {
		OrderedTest orderedTest = orderedTestService.fetchOrderedTestDetails(appointmentBookingId);
		if (Objects.isNull(orderedTest)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(orderedTest, HttpStatus.OK);
	}

	@PostMapping()
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<OrderedTest> uploadOrderedTest(@RequestBody OrderedTestBean orderedTestBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		return new ResponseEntity<>(orderedTestService.uploadOrderedTest(orderedTestBean), HttpStatus.CREATED);
	}

	@PostMapping(value = "/result")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<EmailAudit> notifyTestResult(@RequestBody TestResultBean testResultBean)
			throws InvalidInputException {
		return new ResponseEntity<>(orderedTestService.notifyTestResult(testResultBean), HttpStatus.CREATED);
	}

}
