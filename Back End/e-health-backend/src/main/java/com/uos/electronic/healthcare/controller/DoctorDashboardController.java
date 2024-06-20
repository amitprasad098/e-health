package com.uos.electronic.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.service.DoctorDashboardService;

@RestController
public class DoctorDashboardController {
	
	@Autowired
	private DoctorDashboardService doctorDashboardService;
	
	@GetMapping(value = "/appointments/requests/{doctor-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<AppointmentBooking>> fetchApprovedAppointmentRequests(@PathVariable(name = "doctor-id") int doctorId) {
		return new ResponseEntity<>(doctorDashboardService.fetchApprovedAppointmentRequests(doctorId), HttpStatus.OK);
	}

}
