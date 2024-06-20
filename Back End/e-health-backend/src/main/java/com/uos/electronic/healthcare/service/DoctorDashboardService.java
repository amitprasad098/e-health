package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.AppointmentBooking;

public interface DoctorDashboardService {
	
	List<AppointmentBooking> fetchApprovedAppointmentRequests(int doctorId);

}
