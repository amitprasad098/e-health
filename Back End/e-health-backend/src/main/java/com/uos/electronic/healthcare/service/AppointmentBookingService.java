package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.NoAvailableStaffException;
import com.uos.electronic.healthcare.model.AppointmentBookingBean;

public interface AppointmentBookingService {

	AppointmentBooking bookAnAppointment(AppointmentBookingBean appointmentBookingBean)
			throws InvalidInputException, DateExtractionException, NoAvailableStaffException;
	
	List<AppointmentBooking> fetchAllPatientAppointments(int patientId);

}
