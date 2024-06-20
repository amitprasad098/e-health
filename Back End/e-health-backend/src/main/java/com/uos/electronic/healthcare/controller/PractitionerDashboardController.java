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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.Doctor;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.AlternativeAppointmentBean;
import com.uos.electronic.healthcare.service.PractitionerDashboardService;

@RestController
@RequestMapping(value = "/appointments/requests")
public class PractitionerDashboardController {

	@Autowired
	private PractitionerDashboardService practitionerDashboardService;

	@GetMapping(value = "/{practitioner-id}/{appointment-status}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<AppointmentBooking>> fetchAppointmentRequestsByStatus(
			@PathVariable(name = "practitioner-id") int practitionerId,
			@PathVariable(name = "appointment-status") String appointmentStatus) {
		return new ResponseEntity<>(
				practitionerDashboardService.fetchAppointmentRequestsByStatus(practitionerId, appointmentStatus),
				HttpStatus.OK);
	}

	@GetMapping(value = "/{practice-id}/doctors")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<Doctor>> fetchDoctorsByPractice(@PathVariable(name = "practice-id") int practiceId)
			throws InvalidInputException {
		return new ResponseEntity<>(practitionerDashboardService.fetchDoctorsByPractice(practiceId), HttpStatus.OK);
	}

	@PutMapping(value = "/approve/{appointment-id}/doctor/{doctor-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> approveAppointmentRequests(
			@PathVariable(name = "appointment-id") int appointmentBookingId,
			@PathVariable(name = "doctor-id") int doctorId) throws InvalidInputException {
		practitionerDashboardService.approveAppointmentRequests(appointmentBookingId, doctorId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/decline/{appointment-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> declineAppointmentRequests(
			@PathVariable(name = "appointment-id") int appointmentBookingId) throws InvalidInputException {
		practitionerDashboardService.declineAppointmentRequests(appointmentBookingId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/alternative")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<String> offerAlternateAppointment(
			@RequestBody AlternativeAppointmentBean alternativeAppointmentBean) throws InvalidInputException {
		practitionerDashboardService.offerAlternateAppointment(alternativeAppointmentBean);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/tests/{practice-id}")
	@CrossOrigin(origins = {
			"http://electronic-healthcare-s3.s3-website.eu-north-1.amazonaws.com/",
			"http://localhost:5173"
	})
	public ResponseEntity<List<OrderedTest>> fetchTestAppointmentsByPractice(@PathVariable(name = "practice-id") int practiceId)
			throws InvalidInputException {
		return new ResponseEntity<>(practitionerDashboardService.fetchTestAppointmentsByPractice(practiceId), HttpStatus.OK);
	}

}
