package com.uos.electronic.healthcare.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.DoctorRegistrationBean;
import com.uos.electronic.healthcare.model.PracticeRegistrationBean;
import com.uos.electronic.healthcare.model.PractitionerRegistrationBean;
import com.uos.electronic.healthcare.model.UpdateUserPasswordBean;
import com.uos.electronic.healthcare.service.AdminDashboardService;

@ExtendWith(MockitoExtension.class)
class AdminDashboardControllerTest {

    @Mock
    private AdminDashboardService adminDashboardService;

    @InjectMocks
    private AdminDashboardController adminDashboardController;

    @Test
    void testFetchPendingRegistrationRequests() {
        List<PatientPracticeRegistration> registrationList = new ArrayList<>();
        PatientPracticeRegistration registration = new PatientPracticeRegistration();
        registrationList.add(registration);

        when(adminDashboardService.fetchPendingRegistrationRequests()).thenReturn(registrationList);

        ResponseEntity<List<PatientPracticeRegistration>> response = adminDashboardController.fetchPendingRegistrationRequests();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registrationList, response.getBody());
    }

    @Test
    void testApprovePendingRegistrationRequests() throws InvalidInputException {
        doNothing().when(adminDashboardService).approvePendingRegistrationRequests(1);

        ResponseEntity<String> response = adminDashboardController.approvePendingRegistrationRequests(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Constants.REQUEST_APPROVED, response.getBody());
    }

    @Test
    void testDeclinePendingRegistrationRequests() throws InvalidInputException {
        doNothing().when(adminDashboardService).declinePendingRegistrationRequests(1);

        ResponseEntity<String> response = adminDashboardController.declinePendingRegistrationRequests(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Constants.REQUEST_DECLINED, response.getBody());
    }

    @Test
    void testAddNewPractice() {
        PracticeRegistrationBean practiceRegistrationBean = new PracticeRegistrationBean();
        doNothing().when(adminDashboardService).addNewPractice(practiceRegistrationBean);

        ResponseEntity<String> response = adminDashboardController.addNewPractice(practiceRegistrationBean);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddNewDoctor() {
        DoctorRegistrationBean doctorRegistrationBean = new DoctorRegistrationBean();
        doNothing().when(adminDashboardService).addNewDoctor(doctorRegistrationBean);

        ResponseEntity<String> response = adminDashboardController.addNewDoctor(doctorRegistrationBean);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testFetchPracticesWithoutPractitioner() {
        List<Practice> practiceList = new ArrayList<>();
        Practice practice = new Practice();
        practiceList.add(practice);

        when(adminDashboardService.fetchPracticesWithoutPractitioner()).thenReturn(practiceList);

        ResponseEntity<List<Practice>> response = adminDashboardController.fetchPracticesWithoutPractitioner();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(practiceList, response.getBody());
    }

    @Test
    void testAddNewPractitioner() {
        PractitionerRegistrationBean practitionerRegistrationBean = new PractitionerRegistrationBean();
        doNothing().when(adminDashboardService).addNewPractitioner(practitionerRegistrationBean);

        ResponseEntity<String> response = adminDashboardController.addNewPractitioner(practitionerRegistrationBean);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateUserPassword() throws InvalidInputException {
        UpdateUserPasswordBean updateUserPasswordBean = new UpdateUserPasswordBean();
        doNothing().when(adminDashboardService).updateUserPassword(updateUserPasswordBean);

        ResponseEntity<String> response = adminDashboardController.updateUserPassword(updateUserPasswordBean);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password Updated", response.getBody());
    }

    @Test
    void testUpdateUserPasswordInvalidInputException() throws InvalidInputException {
        UpdateUserPasswordBean updateUserPasswordBean = new UpdateUserPasswordBean();
        doThrow(new InvalidInputException("Invalid input")).when(adminDashboardService).updateUserPassword(updateUserPasswordBean);

        InvalidInputException exception = null;
        try {
            adminDashboardController.updateUserPassword(updateUserPasswordBean);
        } catch (InvalidInputException e) {
            exception = e;
        }
        assertEquals("Invalid input", exception.getMessage());
    }
}
