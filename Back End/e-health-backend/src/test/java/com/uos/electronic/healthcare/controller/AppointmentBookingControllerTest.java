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
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.NoAvailableStaffException;
import com.uos.electronic.healthcare.model.AppointmentBookingBean;
import com.uos.electronic.healthcare.service.AppointmentBookingService;

@ExtendWith(MockitoExtension.class)
class AppointmentBookingControllerTest {

    @Mock
    private AppointmentBookingService appointmentBookingService;

    @InjectMocks
    private AppointmentBookingController appointmentBookingController;

    @Test
    void testBookAnAppointment() throws InvalidInputException, DateExtractionException, NoAvailableStaffException {
        AppointmentBookingBean appointmentBookingBean = new AppointmentBookingBean();
        AppointmentBooking appointmentBooking = new AppointmentBooking();
        
        when(appointmentBookingService.bookAnAppointment(appointmentBookingBean)).thenReturn(appointmentBooking);

        ResponseEntity<AppointmentBooking> response = appointmentBookingController.bookAnAppointment(appointmentBookingBean);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(appointmentBooking, response.getBody());
    }

    @Test
    void testFetchAllPatientAppointments() {
        List<AppointmentBooking> appointmentBookingList = new ArrayList<>();
        AppointmentBooking appointmentBooking = new AppointmentBooking();
        appointmentBookingList.add(appointmentBooking);
        
        when(appointmentBookingService.fetchAllPatientAppointments(1)).thenReturn(appointmentBookingList);

        ResponseEntity<List<AppointmentBooking>> response = appointmentBookingController.fetchAllPatientAppointments(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentBookingList, response.getBody());
    }
}
