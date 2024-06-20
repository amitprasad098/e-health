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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.NoAvailableStaffException;
import com.uos.electronic.healthcare.model.AppointmentBookingBean;
import com.uos.electronic.healthcare.service.AppointmentBookingService;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentBookingController {

	@Autowired
	private AppointmentBookingService appointmentBookingService;

	@PostMapping
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<AppointmentBooking> bookAnAppointment(
			@RequestBody AppointmentBookingBean appointmentBookingBean)
			throws InvalidInputException, DateExtractionException, NoAvailableStaffException {
		return new ResponseEntity<>(appointmentBookingService.bookAnAppointment(appointmentBookingBean),
				HttpStatus.CREATED);
	}

	@GetMapping(value = "/{patient-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<AppointmentBooking>> fetchAllPatientAppointments(
			@PathVariable(name = "patient-id") int patientId) {
		return new ResponseEntity<>(appointmentBookingService.fetchAllPatientAppointments(patientId), HttpStatus.OK);
	}

}