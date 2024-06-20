package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.Doctor;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.AlternativeAppointmentBean;

public interface PractitionerDashboardService {
	
	List<AppointmentBooking> fetchAppointmentRequestsByStatus(int practitionerId, String appointmentStatus);
	
	void approveAppointmentRequests(int appointmentBookingId, int doctorId) throws InvalidInputException;
	
	void declineAppointmentRequests(int appointmentBookingId) throws InvalidInputException;
	
	void offerAlternateAppointment(AlternativeAppointmentBean alternativeAppointmentBean) throws InvalidInputException;
	
	List<Doctor> fetchDoctorsByPractice(int practiceId) throws InvalidInputException;
	
	List<OrderedTest> fetchTestAppointmentsByPractice(int practiceId);

}
