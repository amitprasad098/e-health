package com.uos.electronic.healthcare.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;

@ExtendWith(MockitoExtension.class)
class DoctorDashboardServiceImplTest {

    @Mock
    private AppointmentBookingRepository appointmentBookingRepository;

    @InjectMocks
    private DoctorDashboardServiceImpl doctorDashboardService;

    @Test
    void testFetchApprovedAppointmentRequests() {
        int doctorId = 1;
        List<AppointmentBooking> appointmentBookingList = new ArrayList<>();
        AppointmentBooking appointmentBooking = new AppointmentBooking();
        appointmentBooking.setAppointmentBookingId(1);
        appointmentBooking.setAppointmentDetails("Appointment Details");
        appointmentBookingList.add(appointmentBooking);

        // Mock the repository method
        when(appointmentBookingRepository.findByDoctorId(doctorId)).thenReturn(appointmentBookingList);

        // Call the service method and assert the response
        List<AppointmentBooking> result = doctorDashboardService.fetchApprovedAppointmentRequests(doctorId);
        assertEquals(appointmentBookingList, result);
    }
}
