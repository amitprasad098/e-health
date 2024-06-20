package com.uos.electronic.healthcare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.Doctor;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.entity.Practitioner;
import com.uos.electronic.healthcare.entity.User;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.DoctorRegistrationBean;
import com.uos.electronic.healthcare.model.PracticeRegistrationBean;
import com.uos.electronic.healthcare.model.PractitionerRegistrationBean;
import com.uos.electronic.healthcare.model.UpdateUserPasswordBean;
import com.uos.electronic.healthcare.repository.DoctorRepository;
import com.uos.electronic.healthcare.repository.PatientPracticeRegistrationRepository;
import com.uos.electronic.healthcare.repository.PracticeRepository;
import com.uos.electronic.healthcare.repository.PractitionerRepository;
import com.uos.electronic.healthcare.repository.UserRepository;
import com.uos.electronic.healthcare.repository.UserTypeRepository;
import com.uos.electronic.healthcare.service.AdminDashboardService;
import com.uos.electronic.healthcare.service.EmailService;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

	@Autowired
	private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;

	@Autowired
	private PracticeRepository practiceRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PractitionerRepository practitionerRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public List<PatientPracticeRegistration> fetchPendingRegistrationRequests() {
		return patientPracticeRegistrationRepository.findByRegistrationStatus(Constants.PENDING_STATUS);
	}

	@Override
	public void approvePendingRegistrationRequests(int patientPracticeRegistrationId) throws InvalidInputException {
		Optional<PatientPracticeRegistration> patientPracticeRegistrationOptional = patientPracticeRegistrationRepository
				.findById(patientPracticeRegistrationId);
		if (!patientPracticeRegistrationOptional.isPresent()) {
			throw new InvalidInputException(Constants.REQUEST_DOES_NOT_EXIST_MESSAGE);
		}
		PatientPracticeRegistration userPracticeRegistration = patientPracticeRegistrationOptional.get();
		userPracticeRegistration.setRegistrationStatus(Constants.APPROVED_STATUS);
		patientPracticeRegistrationRepository.save(userPracticeRegistration);
		
		String emailBody = "Your registration request to the Practice - " + userPracticeRegistration.getPractice().getPracticeName() + " has been approved. You can book appointments now.";
		emailService.sendSimpleMail(generateEmailDetails(userPracticeRegistration.getPatient().getUser().getUserEmail(), Constants.EMAIL_SUBJECT_REGISTRATION_APPROVED, emailBody));
	}

	@Override
	public void declinePendingRegistrationRequests(int patientPracticeRegistrationId) throws InvalidInputException {
		Optional<PatientPracticeRegistration> patientPracticeRegistrationOptional = patientPracticeRegistrationRepository
				.findById(patientPracticeRegistrationId);
		if (!patientPracticeRegistrationOptional.isPresent()) {
			throw new InvalidInputException(Constants.REQUEST_DOES_NOT_EXIST_MESSAGE);
		}
		PatientPracticeRegistration patientPracticeRegistration = patientPracticeRegistrationOptional.get();
		patientPracticeRegistration.setRegistrationStatus(Constants.DECLINED_STATUS);
		patientPracticeRegistrationRepository.save(patientPracticeRegistration);
		
		String emailBody = "Your registration request to the Practice - " + patientPracticeRegistration.getPractice().getPracticeName() + " has been declined.";
		emailService.sendSimpleMail(generateEmailDetails(patientPracticeRegistration.getPatient().getUser().getUserEmail(), Constants.EMAIL_SUBJECT_REGISTRATION_DECLINED, emailBody));
	}

	@Override
	public void addNewPractice(PracticeRegistrationBean practiceRegistrationBean) {
		Practice practice = new Practice();
		practice.setPracticeName(practiceRegistrationBean.getPracticeName());
		practice.setPracticeDescription(practiceRegistrationBean.getPracticeDescription());
		practice.setPracticeAddress(practiceRegistrationBean.getPracticeAddress());
		practice.setPracticeContactDetails(practiceRegistrationBean.getPracticeContactDetails());
		practice.setPracticeImageUrl(practiceRegistrationBean.getPracticeImageUrl());
		practiceRepository.save(practice);
	}

	@Override
	public void addNewDoctor(DoctorRegistrationBean doctorRegistrationBean) {
		User user = new User();
		Doctor doctor = new Doctor();
		user.setUserFullName(doctorRegistrationBean.getDoctorName());
		user.setUserEmail(doctorRegistrationBean.getDoctorEmail());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(doctorRegistrationBean.getDoctorPassword()));

		user.setUserType(userTypeRepository.findByUserRole(Constants.DOCTOR_USER_ROLE).orElse(null));
		User registeredUser = userRepository.save(user);

		doctor.setUser(registeredUser);
		doctor.setPractice(
				practiceRepository.findByPracticeName(doctorRegistrationBean.getPracticeName()).orElse(null));
		doctor.setDoctorDegree(doctorRegistrationBean.getDoctorDegree());
		doctor.setDoctorSpeciality(doctorRegistrationBean.getDoctorSpeciality());
		doctorRepository.save(doctor);
	}

	@Override
	public List<Practice> fetchPracticesWithoutPractitioner() {
		List<Practice> practicesWithoutPractitioner = new ArrayList<>();
		List<Practice> allPractices = practiceRepository.findAll();
		List<Practice> practicesInPractionerRepository = practitionerRepository.findAll().stream()
				.map(Practitioner::getPractice).collect(Collectors.toList());

		allPractices.forEach(practice -> {
			if (!practicesInPractionerRepository.contains(practice)) {
				practicesWithoutPractitioner.add(practice);
			}
		});
		return practicesWithoutPractitioner;
	}

	@Override
	public void addNewPractitioner(PractitionerRegistrationBean practitionerRegistrationBean) {
		User user = new User();
		Practitioner practitioner = new Practitioner();
		user.setUserFullName(practitionerRegistrationBean.getPractitionerName());
		user.setUserEmail(practitionerRegistrationBean.getPractitionerEmail());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(practitionerRegistrationBean.getPractitionerPassword()));

		user.setUserType(userTypeRepository.findByUserRole(Constants.PRACTITIONER_USER_ROLE).orElse(null));
		User registeredUser = userRepository.save(user);

		practitioner.setUser(registeredUser);
		practitioner.setPractice(
				practiceRepository.findByPracticeName(practitionerRegistrationBean.getPracticeName()).orElse(null));
		practitioner.setPractitionerDegree(practitionerRegistrationBean.getPractitionerDegree());
		practitionerRepository.save(practitioner);
	}

	@Override
	public void updateUserPassword(UpdateUserPasswordBean updateUserPasswordBean) throws InvalidInputException {
		User user = userRepository.findByUserEmail(updateUserPasswordBean.getUserEmail())
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(updateUserPasswordBean.getUserPassword()));
		
		userRepository.save(user);
	}
	
	private EmailDetails generateEmailDetails(String emailRecipient, String emailSubject, String emailBody) {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(emailRecipient);
		emailDetails.setSubject(emailSubject);
		emailDetails.setMsgBody(emailBody);
		
		return emailDetails;
	}

}