package com.uos.electronic.healthcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.EmailAudit;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.Patient;
import com.uos.electronic.healthcare.entity.User;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.GDPRViewRequestResponseBean;
import com.uos.electronic.healthcare.model.UpdatePatientDataBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.EmailAuditRepository;
import com.uos.electronic.healthcare.repository.EmailAuditTypeRepository;
import com.uos.electronic.healthcare.repository.MedicalHistoryRepository;
import com.uos.electronic.healthcare.repository.OrderedTestRepository;
import com.uos.electronic.healthcare.repository.PatientPracticeRegistrationRepository;
import com.uos.electronic.healthcare.repository.PatientRepository;
import com.uos.electronic.healthcare.repository.PrescriptionRepository;
import com.uos.electronic.healthcare.repository.SpecialistReferralRepository;
import com.uos.electronic.healthcare.repository.UserRepository;
import com.uos.electronic.healthcare.service.DataRegulationService;
import com.uos.electronic.healthcare.service.EmailService;

@Service
public class DataRegulationServiceImpl implements DataRegulationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private EmailAuditTypeRepository emailAuditTypeRepository;

	@Autowired
	private EmailAuditRepository emailAuditRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;

	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private OrderedTestRepository orderedTestRepository;

	@Autowired
	private SpecialistReferralRepository specialistReferralRepository;

	@Value("${spring.mail.username}")
	private String emailSender;

	@Override
	public void viewUserData(int userId) throws InvalidInputException {
		Patient patient = patientRepository.findByUserId(userId)
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));

		GDPRViewRequestResponseBean gdprViewRequestResponseBean = GDPRViewRequestResponseBean.builder()
				.patientName(patient.getUser().getUserFullName()).patientEmail(patient.getUser().getUserEmail())
				.patientAddress(patient.getPatientAddress())
				.patientDateOfBirth(String.valueOf(patient.getPatientDateOfBirth()))
				.patientGender(patient.getPatientGender()).patientContactNumber(patient.getPatientContactNumber())
				.build();

		String emailBody = "E-Health has the following the data. \n Patient Name: " + gdprViewRequestResponseBean.getPatientName() + " \n Patient Email: " + gdprViewRequestResponseBean.getPatientEmail() + " \n Patient Adderss: " + gdprViewRequestResponseBean.getPatientAddress() + "\n Patient Date of Birth: " + gdprViewRequestResponseBean.getPatientDateOfBirth() + "\n Patient Gender: " + gdprViewRequestResponseBean.getPatientGender() + "\n Patient Contact Number: " + gdprViewRequestResponseBean.getPatientContactNumber();
		emailService.sendSimpleMail(generateEmailDetails(patient.getUser().getUserEmail(), Constants.EMAIL_SUBJECT_GDPR_VIEW_REQUEST, emailBody));

		EmailAudit emailAudit = new EmailAudit();

		emailAudit.setEmailAuditType(
				emailAuditTypeRepository.findByEmailAuditTypeName(Constants.EMAIL_AUDIT_TYPE_TEST_RESULT)
						.orElseThrow(() -> new InvalidInputException(Constants.EMAIL_AUDIT_TYPE_DOES_NOT_EXIST)));
		emailAudit.setEmailMessage(patient.toString());
		emailAudit.setEmailReceiver(patient.getUser());
		User emailSenderUser = userRepository.findByUserEmail(emailSender)
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));
		emailAudit.setEmailSender(emailSenderUser);
		emailAudit.setEmailSubject("View Request Data Response");

		emailAuditRepository.save(emailAudit);
	}

	@Override
	public void updateUserData(UpdatePatientDataBean updatePatientDataBean) throws InvalidInputException {
		Patient patient = patientRepository.findByUserId(updatePatientDataBean.getUserId())
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));

		User user = patient.getUser();
		if (!updatePatientDataBean.getPatientName().isEmpty()) {
			user.setUserFullName(updatePatientDataBean.getPatientName());
		}

		if (!updatePatientDataBean.getPatientEmail().isEmpty()) {
			user.setUserEmail(updatePatientDataBean.getPatientEmail());
		}

		if (!updatePatientDataBean.getPatientContactNumber().isEmpty()) {
			patient.setPatientContactNumber(updatePatientDataBean.getPatientContactNumber());
		}

		if (!updatePatientDataBean.getPatientAddress().isEmpty()) {
			patient.setPatientAddress(updatePatientDataBean.getPatientAddress());
		}

		if (!updatePatientDataBean.getPatientPassword().isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setUserPassword(passwordEncoder.encode(updatePatientDataBean.getPatientPassword()));
		}

		userRepository.save(user);

		patient.setUser(user);
		patientRepository.save(patient);
		
		String emailBody = "Your account details have been updated successfully.";
		emailService.sendSimpleMail(generateEmailDetails(patient.getUser().getUserEmail(), Constants.EMAIL_SUBJECT_GDPR_UPDATE_REQUEST, emailBody));
	}

	@Override
	public void deleteUserData(int userId) throws InvalidInputException {
		Patient patient = patientRepository.findByUserId(userId)
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));
		
		emailAuditRepository.deleteRecordsByUserId(userId);
		specialistReferralRepository.deleteRecordsByUserId(userId);
		orderedTestRepository.deleteRecordsByUserId(userId);
		prescriptionRepository.deleteRecordsByUserId(userId);
		medicalHistoryRepository.deleteRecordsByUserId(userId);
		appointmentBookingRepository.deleteRecordsByUserId(userId);
		patientPracticeRegistrationRepository.deleteRecordsByUserId(userId);
		patientRepository.deleteRecordsByUserId(userId);
		userRepository.deleteRecordsByUserId(userId);
		
		String emailBody = "Your account has been deleted successfully. All data stored has been removed now. Thank you.";
		emailService.sendSimpleMail(generateEmailDetails(patient.getUser().getUserEmail(), Constants.EMAIL_SUBJECT_GDPR_DELETE_REQUEST, emailBody));
	}
	
	private EmailDetails generateEmailDetails(String emailRecipient, String emailSubject, String emailBody) {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(emailRecipient);
		emailDetails.setSubject(emailSubject);
		emailDetails.setMsgBody(emailBody);
		
		return emailDetails;
	}

}
