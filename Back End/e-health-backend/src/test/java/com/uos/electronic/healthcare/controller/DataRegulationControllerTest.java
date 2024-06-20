package com.uos.electronic.healthcare.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.UpdatePatientDataBean;
import com.uos.electronic.healthcare.service.DataRegulationService;

@ExtendWith(MockitoExtension.class)
class DataRegulationControllerTest {

    @Mock
    private DataRegulationService dataRegulationService;

    @InjectMocks
    private DataRegulationController dataRegulationController;

    @Test
    void testViewUserData() throws InvalidInputException {
        doNothing().when(dataRegulationService).viewUserData(1);

        ResponseEntity<String> response = dataRegulationController.viewUserData(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    void testViewUserDataInvalidInputException() throws InvalidInputException {
        doThrow(new InvalidInputException("Invalid input")).when(dataRegulationService).viewUserData(1);

        InvalidInputException exception = null;
        try {
            dataRegulationController.viewUserData(1);
        } catch (InvalidInputException e) {
            exception = e;
        }
        assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    void testUpdateUserData() throws InvalidInputException {
        UpdatePatientDataBean updatePatientDataBean = new UpdatePatientDataBean();
        doNothing().when(dataRegulationService).updateUserData(updatePatientDataBean);

        ResponseEntity<String> response = dataRegulationController.updateUserData(updatePatientDataBean);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
    }

    @Test
    void testUpdateUserDataInvalidInputException() throws InvalidInputException {
        UpdatePatientDataBean updatePatientDataBean = new UpdatePatientDataBean();
        doThrow(new InvalidInputException("Invalid input")).when(dataRegulationService).updateUserData(updatePatientDataBean);

        InvalidInputException exception = null;
        try {
            dataRegulationController.updateUserData(updatePatientDataBean);
        } catch (InvalidInputException e) {
            exception = e;
        }
        assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    void testDeleteUserData() throws InvalidInputException {
        doNothing().when(dataRegulationService).deleteUserData(1);

        ResponseEntity<String> response = dataRegulationController.deleteUserData(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account deleted successfully", response.getBody());
    }

    @Test
    void testDeleteUserDataInvalidInputException() throws InvalidInputException {
        doThrow(new InvalidInputException("Invalid input")).when(dataRegulationService).deleteUserData(1);

        InvalidInputException exception = null;
        try {
            dataRegulationController.deleteUserData(1);
        } catch (InvalidInputException e) {
            exception = e;
        }
        assertEquals("Invalid input", exception.getMessage());
    }
}
