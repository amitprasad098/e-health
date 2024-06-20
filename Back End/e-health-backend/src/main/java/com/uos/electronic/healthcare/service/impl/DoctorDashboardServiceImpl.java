package com.uos.electronic.healthcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.service.DoctorDashboardService;

@Service
public class DoctorDashboardServiceImpl implements DoctorDashboardService {

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;

	@Override
	public List<AppointmentBooking> fetchApprovedAppointmentRequests(int doctorId) {

		return appointmentBookingRepository.findByDoctorId(doctorId);
	}

}
