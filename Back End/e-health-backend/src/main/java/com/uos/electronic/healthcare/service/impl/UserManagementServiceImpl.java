package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.Patient;
import com.uos.electronic.healthcare.entity.User;
import com.uos.electronic.healthcare.entity.UserType;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientRegistrationBean;
import com.uos.electronic.healthcare.model.UserSignInBean;
import com.uos.electronic.healthcare.repository.AdminRepository;
import com.uos.electronic.healthcare.repository.DoctorRepository;
import com.uos.electronic.healthcare.repository.PatientRepository;
import com.uos.electronic.healthcare.repository.PractitionerRepository;
import com.uos.electronic.healthcare.repository.UserRepository;
import com.uos.electronic.healthcare.repository.UserTypeRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.UserManagementService;

@Service
public class UserManagementServiceImpl implements UserManagementService, UserDetailsService {

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PractitionerRepository practitionerRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public List<UserType> fetchUserTypes() {
		return userTypeRepository.findAll().stream().collect(Collectors.toList());
	}

	@Override
	public Patient registerPatient(PatientRegistrationBean patientRegistrationBean) throws DateExtractionException {
		User user = new User();
		Patient patient = new Patient();
		user.setUserFullName(patientRegistrationBean.getPatientName());
		user.setUserEmail(patientRegistrationBean.getPatientEmail());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(patientRegistrationBean.getPatientPassword()));

		user.setUserType(userTypeRepository.findByUserRole(Constants.PATIENT_USER_ROLE).orElse(null));
		User registeredUser = userRepository.save(user);
		patient.setUser(registeredUser);
		patient.setPatientAddress(patientRegistrationBean.getPatientAddress());
		patient.setPatientGender(patientRegistrationBean.getPatientGender());
		patient.setPatientContactNumber(patientRegistrationBean.getPatientContactNumber());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date patientDateOfBirth = null;
		try {
			patientDateOfBirth = simpleDateFormat.parse(patientRegistrationBean.getPatientDateOfBirth());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		patient.setPatientDateOfBirth(patientDateOfBirth);
		Patient registeredPatient = patientRepository.save(patient);
		
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(registeredPatient.getUser().getUserEmail());
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_REGISTER_USER);
		String registeredUserEmailMessage = "Welcome " + registeredPatient.getUser().getUserFullName() + " to E-Health. You can use the functionalities of registering to a Practice, booking appointments, and find pharmacies.";
		emailDetails.setMsgBody(registeredUserEmailMessage);
		emailService.sendSimpleMail(emailDetails);
		
		return registeredPatient;

	}

	@Override
	public Object signInUser(UserSignInBean userSignInBean) throws InvalidInputException {
		List<User> users = userRepository.findAll();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		List<User> signedInUserList = users.stream()
				.filter(user -> user.getUserEmail().equals(userSignInBean.getUserEmail()))
				.filter(user -> passwordEncoder.matches(userSignInBean.getUserPassword(), user.getUserPassword()))
				.filter(user -> user.getUserType().getUserTypeName().equals(userSignInBean.getUserRole()))
				.collect(Collectors.toList());

		if (signedInUserList.isEmpty()) {
			throw new InvalidInputException(Constants.INVALID_CREDENTIALS_MESSAGE);
		}

		User signedInUser = signedInUserList.get(Constants.FIRST_INDEX_ELEMENT);

		return userDetails(signedInUser);

	}

	private Object userDetails(User signedInUser) {
		if (signedInUser.getUserType().getUserTypeName().equals(Constants.ADMINT_USER_ROLE)) {
			return adminRepository.findByUserId(signedInUser.getUserId()).orElse(null);
		}

		else if (signedInUser.getUserType().getUserTypeName().equals(Constants.DOCTOR_USER_ROLE)) {
			return doctorRepository.findByUserId(signedInUser.getUserId()).orElse(null);
		}

		else if (signedInUser.getUserType().getUserTypeName().equals(Constants.PATIENT_USER_ROLE)) {
			return patientRepository.findByUserId(signedInUser.getUserId()).orElse(null);
		}

		else {
			return practitionerRepository.findByUserId(signedInUser.getUserId()).orElse(null);
		}
	}

	@Override
	public void forgotPasswordManagement(String userEmail) throws InvalidInputException {
		EmailDetails emailDetails = new EmailDetails();
		Random randomGenerator = new Random();
		Optional<User> userOptional = userRepository.findByUserEmail(userEmail);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			emailDetails.setRecipient(userEmail);
			emailDetails.setSubject(Constants.RESET_PASSWORD_EMAIL_SUBJECT);
			String numbers = Constants.ALL_DIGITS;
			char[] otp = new char[6];
			for (int i = 0; i < 6; i++) {
				otp[i] = numbers.charAt(randomGenerator.nextInt(numbers.length()));
			}
			String generatedOtp = new String(otp);
			String resetPasswordEmailMessage = "The temporary password for the account linked with " + userEmail  + " is : " + generatedOtp + 
					". Please update your password once logged in.";
			emailDetails.setMsgBody(resetPasswordEmailMessage);
			emailService.sendSimpleMail(emailDetails);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setUserPassword(passwordEncoder.encode(generatedOtp));
			userRepository.save(user);
		} else {
			throw new InvalidInputException(Constants.EMAIL_ADDRESS_DOES_NOT_EXIST);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUserEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(Constants.USER_DOES_NOT_EXIST_MESSAGE));
	}

}