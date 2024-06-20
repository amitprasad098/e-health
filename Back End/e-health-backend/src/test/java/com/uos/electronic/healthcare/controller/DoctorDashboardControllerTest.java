package com.uos.electronic.healthcare.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.service.DoctorDashboardService;

@ExtendWith(MockitoExtension.class)
class DoctorDashboardControllerTest {
    
    @Mock
    private DoctorDashboardService doctorDashboardService;
    
    @InjectMocks
    private DoctorDashboardController dashboardController;

    @Test
    void testFetchApprovedAppointmentRequests() {
        // Setup mock data
        AppointmentBooking appointmentBooking = new AppointmentBooking();
        appointmentBooking.setAppointmentBookingId(1);
        appointmentBooking.setAppointmentDetails("Appointment Details");
        List<AppointmentBooking> appointmentBookingList = new ArrayList<>();
        appointmentBookingList.add(appointmentBooking);
        
        // Mock the service method
        when(doctorDashboardService.fetchApprovedAppointmentRequests(1)).thenReturn(appointmentBookingList);
        
        // Call the controller method and assert the response
        ResponseEntity<List<AppointmentBooking>> response = dashboardController.fetchApprovedAppointmentRequests(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentBookingList, response.getBody());
    }
}
