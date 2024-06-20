package com.uos.electronic.healthcare.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.*;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.*;
import com.uos.electronic.healthcare.repository.*;
import com.uos.electronic.healthcare.service.EmailService;

@ExtendWith(MockitoExtension.class)
class AdminDashboardServiceImplTest {

    @Mock
    private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;

    @Mock
    private PracticeRepository practiceRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PractitionerRepository practitionerRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdminDashboardServiceImpl adminDashboardService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testFetchPendingRegistrationRequests() {
        List<PatientPracticeRegistration> pendingRegistrations = new ArrayList<>();
        when(patientPracticeRegistrationRepository.findByRegistrationStatus(Constants.PENDING_STATUS)).thenReturn(pendingRegistrations);

        List<PatientPracticeRegistration> result = adminDashboardService.fetchPendingRegistrationRequests();
        assertEquals(pendingRegistrations, result);
    }

    @Test
    void testApprovePendingRegistrationRequests() throws InvalidInputException {
        PatientPracticeRegistration registration = new PatientPracticeRegistration();
        Practice practice = new Practice();
        practice.setPracticeName("Test Practice");
        User patient = new User();
        patient.setUserEmail("test@example.com");
        registration.setPatient(new Patient(patient));
        registration.setPractice(practice);
        registration.setRegistrationStatus(Constants.PENDING_STATUS);
        when(patientPracticeRegistrationRepository.findById(1)).thenReturn(Optional.of(registration));

        adminDashboardService.approvePendingRegistrationRequests(1);

        assertEquals(Constants.APPROVED_STATUS, registration.getRegistrationStatus());
        verify(patientPracticeRegistrationRepository).save(registration);
        verify(emailService).sendSimpleMail(any(EmailDetails.class));
    }

    @Test
    void testApprovePendingRegistrationRequests_InvalidInput() {
        when(patientPracticeRegistrationRepository.findById(1)).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            adminDashboardService.approvePendingRegistrationRequests(1);
        });

        assertEquals(Constants.REQUEST_DOES_NOT_EXIST_MESSAGE, exception.getMessage());
    }

    @Test
    void testDeclinePendingRegistrationRequests() throws InvalidInputException {
        PatientPracticeRegistration registration = new PatientPracticeRegistration();
        Practice practice = new Practice();
        practice.setPracticeName("Test Practice");
        User patient = new User();
        patient.setUserEmail("test@example.com");
        registration.setPatient(new Patient(patient));
        registration.setPractice(practice);
        registration.setRegistrationStatus(Constants.PENDING_STATUS);
        when(patientPracticeRegistrationRepository.findById(1)).thenReturn(Optional.of(registration));

        adminDashboardService.declinePendingRegistrationRequests(1);

        assertEquals(Constants.DECLINED_STATUS, registration.getRegistrationStatus());
        verify(patientPracticeRegistrationRepository).save(registration);
        verify(emailService).sendSimpleMail(any(EmailDetails.class));
    }

    @Test
    void testDeclinePendingRegistrationRequests_InvalidInput() {
        when(patientPracticeRegistrationRepository.findById(1)).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            adminDashboardService.declinePendingRegistrationRequests(1);
        });

        assertEquals(Constants.REQUEST_DOES_NOT_EXIST_MESSAGE, exception.getMessage());
    }

    @Test
    void testAddNewPractice() {
        PracticeRegistrationBean practiceRegistrationBean = new PracticeRegistrationBean();
        practiceRegistrationBean.setPracticeName("Test Practice");
        practiceRegistrationBean.setPracticeDescription("Description");
        practiceRegistrationBean.setPracticeAddress("Address");
        practiceRegistrationBean.setPracticeContactDetails("Contact");
        practiceRegistrationBean.setPracticeImageUrl("Image URL");

        adminDashboardService.addNewPractice(practiceRegistrationBean);

        verify(practiceRepository).save(any(Practice.class));
    }

    @Test
    void testAddNewDoctor() {
        DoctorRegistrationBean doctorRegistrationBean = new DoctorRegistrationBean();
        doctorRegistrationBean.setDoctorName("Doctor Name");
        doctorRegistrationBean.setDoctorEmail("doctor@example.com");
        doctorRegistrationBean.setDoctorPassword("password");
        doctorRegistrationBean.setPracticeName("Practice Name");
        doctorRegistrationBean.setDoctorDegree("MD");
        doctorRegistrationBean.setDoctorSpeciality("Speciality");

        when(userTypeRepository.findByUserRole(Constants.DOCTOR_USER_ROLE)).thenReturn(Optional.of(new UserType()));
        when(practiceRepository.findByPracticeName("Practice Name")).thenReturn(Optional.of(new Practice()));

        adminDashboardService.addNewDoctor(doctorRegistrationBean);

        verify(userRepository).save(any(User.class));
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testFetchPracticesWithoutPractitioner() {
        List<Practice> allPractices = new ArrayList<>();
        Practice practice = new Practice();
        allPractices.add(practice);
        when(practiceRepository.findAll()).thenReturn(allPractices);
        when(practitionerRepository.findAll()).thenReturn(new ArrayList<>());

        List<Practice> result = adminDashboardService.fetchPracticesWithoutPractitioner();
        assertEquals(allPractices, result);
    }

    @Test
    void testAddNewPractitioner() {
        PractitionerRegistrationBean practitionerRegistrationBean = new PractitionerRegistrationBean();
        practitionerRegistrationBean.setPractitionerName("Practitioner Name");
        practitionerRegistrationBean.setPractitionerEmail("practitioner@example.com");
        practitionerRegistrationBean.setPractitionerPassword("password");
        practitionerRegistrationBean.setPracticeName("Practice Name");
        practitionerRegistrationBean.setPractitionerDegree("Degree");

        when(userTypeRepository.findByUserRole(Constants.PRACTITIONER_USER_ROLE)).thenReturn(Optional.of(new UserType()));
        when(practiceRepository.findByPracticeName("Practice Name")).thenReturn(Optional.of(new Practice()));

        adminDashboardService.addNewPractitioner(practitionerRegistrationBean);

        verify(userRepository).save(any(User.class));
        verify(practitionerRepository).save(any(Practitioner.class));
    }

    @Test
    void testUpdateUserPassword() throws InvalidInputException {
        UpdateUserPasswordBean updateUserPasswordBean = new UpdateUserPasswordBean();
        updateUserPasswordBean.setUserEmail("user@example.com");
        updateUserPasswordBean.setUserPassword("newpassword");

        User user = new User();
        when(userRepository.findByUserEmail("user@example.com")).thenReturn(Optional.of(user));

        adminDashboardService.updateUserPassword(updateUserPasswordBean);

        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserPassword_InvalidInput() {
        UpdateUserPasswordBean updateUserPasswordBean = new UpdateUserPasswordBean();
        updateUserPasswordBean.setUserEmail("user@example.com");
        updateUserPasswordBean.setUserPassword("newpassword");

        when(userRepository.findByUserEmail("user@example.com")).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            adminDashboardService.updateUserPassword(updateUserPasswordBean);
        });

        assertEquals(Constants.USER_DOES_NOT_EXIST_MESSAGE, exception.getMessage());
    }
}
