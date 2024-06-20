package com.uos.electronic.healthcare.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.*;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.NoAvailableStaffException;
import com.uos.electronic.healthcare.model.AppointmentBookingBean;
import com.uos.electronic.healthcare.repository.*;
import com.uos.electronic.healthcare.service.EmailService;

@ExtendWith(MockitoExtension.class)
class AppointmentBookingServiceImplTest {

    @Mock
    private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;

    @Mock
    private PractitionerRepository practitionerRepository;

    @Mock
    private AppointmentBookingRepository appointmentBookingRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AppointmentBookingServiceImpl appointmentBookingService;

    private AppointmentBookingBean appointmentBookingBean;
    private PatientPracticeRegistration patientPracticeRegistration;
    private Practitioner practitioner;
    private SimpleDateFormat simpleDateFormat;
    private Date appointmentDate;

    @BeforeEach
    void setUp() throws ParseException {
        simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        appointmentDate = simpleDateFormat.parse("2023-12-31");

        appointmentBookingBean = new AppointmentBookingBean();
        appointmentBookingBean.setPatientId(1);
        appointmentBookingBean.setPracticeId(1);
        appointmentBookingBean.setAppointmentDescription("Description");
        appointmentBookingBean.setAppointmentDate("2023-12-31");
        
        User user = new User();
        user.setUserEmail("practitioner@example.com");
        Patient patient = new Patient();
        patient.setUser(user);
        Practice practice = new Practice();
        practice.setPracticeName("Test Practice");

        patientPracticeRegistration = new PatientPracticeRegistration();
        patientPracticeRegistration.setPatient(patient);
        patientPracticeRegistration.setPractice(practice);

        
        
        practitioner = new Practitioner();
        practitioner.setUser(user);
    }

    @Test
    void testBookAnAppointment() throws InvalidInputException, DateExtractionException, NoAvailableStaffException {
        when(patientPracticeRegistrationRepository.findByPatientIdAndPracticeId(1, 1)).thenReturn(Optional.of(patientPracticeRegistration));
        when(practitionerRepository.findByPracticeId(1)).thenReturn(List.of(practitioner));
        when(appointmentBookingRepository.save(any(AppointmentBooking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentBooking result = appointmentBookingService.bookAnAppointment(appointmentBookingBean);

        assertEquals("Description", result.getAppointmentDetails());
        assertEquals(patientPracticeRegistration, result.getPatientPracticeRegistration());
        assertEquals(Constants.PENDING_STATUS, result.getAppointmentStatus());
        assertEquals(appointmentDate, result.getAppointmentDate());
        assertEquals(practitioner, result.getPractitioner());

        verify(emailService).sendSimpleMail(any(EmailDetails.class));
    }

    @Test
    void testBookAnAppointment_InvalidInputException() {
        when(patientPracticeRegistrationRepository.findByPatientIdAndPracticeId(1, 1)).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            appointmentBookingService.bookAnAppointment(appointmentBookingBean);
        });

        assertEquals(Constants.PATIENT_PRACTICE_REGISTRATION_DOES_NOT_EXIST_MESSAGE, exception.getMessage());
    }

    @Test
    void testBookAnAppointment_DateExtractionException() {
        appointmentBookingBean.setAppointmentDate("invalid-date");

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            appointmentBookingService.bookAnAppointment(appointmentBookingBean);
        });

        assertEquals(Constants.PATIENT_PRACTICE_REGISTRATION_DOES_NOT_EXIST_MESSAGE, exception.getMessage());
    }

    @Test
    void testBookAnAppointment_NoAvailableStaffException() {
        when(patientPracticeRegistrationRepository.findByPatientIdAndPracticeId(1, 1)).thenReturn(Optional.of(patientPracticeRegistration));
        when(practitionerRepository.findByPracticeId(1)).thenReturn(new ArrayList<>());

        NoAvailableStaffException exception = assertThrows(NoAvailableStaffException.class, () -> {
            appointmentBookingService.bookAnAppointment(appointmentBookingBean);
        });

        assertEquals(Constants.PRACTITIONER_DOES_NOT_EXIST, exception.getMessage());
    }

    @Test
    void testFetchAllPatientAppointments() {
        List<AppointmentBooking> appointments = new ArrayList<>();
        when(appointmentBookingRepository.findByPatientId(1)).thenReturn(appointments);

        List<AppointmentBooking> result = appointmentBookingService.fetchAllPatientAppointments(1);
        assertEquals(appointments, result);
    }
}
