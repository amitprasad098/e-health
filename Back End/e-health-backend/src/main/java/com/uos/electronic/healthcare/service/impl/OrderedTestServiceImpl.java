package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.EmailAudit;
import com.uos.electronic.healthcare.entity.EmailAuditType;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.entity.User;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.OrderedTestBean;
import com.uos.electronic.healthcare.model.TestResultBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.EmailAuditRepository;
import com.uos.electronic.healthcare.repository.EmailAuditTypeRepository;
import com.uos.electronic.healthcare.repository.OrderedTestRepository;
import com.uos.electronic.healthcare.repository.UserRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.OrderedTestService;

@Service
public class OrderedTestServiceImpl implements OrderedTestService {

	@Autowired
	private OrderedTestRepository orderedTestRepository;

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailAuditRepository emailAuditRepository;

	@Autowired
	private EmailAuditTypeRepository emailAuditTypeRepository;

	@Value("${spring.mail.username}")
	private String emailSender;

	@Override
	public OrderedTest fetchOrderedTestDetails(int appointmentBookingId) {
		return orderedTestRepository.findByAppointmentBookingId(appointmentBookingId).orElse(null);
	}

	@Override
	public OrderedTest uploadOrderedTest(OrderedTestBean orderedTestBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		if (orderedTestRepository.findByAppointmentBookingId(orderedTestBean.getAppointmentBookingId()).isPresent()) {
			throw new DuplicateEntryException(Constants.ORDERED_TEST_ALREADY_EXIST);
		}
		OrderedTest orderedTest = new OrderedTest();
		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(orderedTestBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}

		orderedTest.setAppointmentBooking(appointmentBookingOptional.get());
		orderedTest.setOrderedTestDetails(orderedTestBean.getOrderedTestDetails());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date orderedTestDate = null;
		try {
			orderedTestDate = simpleDateFormat.parse(orderedTestBean.getOrderedTestDate());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		orderedTest.setOrderedTestDate(orderedTestDate);

		OrderedTest savedOrderedTest = orderedTestRepository.save(orderedTest);
		
		String emailBody = "Please find below the ordered tests for your appointment: " + orderedTest.getAppointmentBooking().getAppointmentDetails() + " with the " + orderedTest.getAppointmentBooking().getPatientPracticeRegistration().getPractice().getPracticeName() + ": \n " + orderedTest.getOrderedTestDetails();
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_TEST_ORDERED);
		emailDetails.setMsgBody(emailBody);
		emailDetails.setRecipient(appointmentBookingOptional.get().getPatientPracticeRegistration().getPatient()
				.getUser().getUserEmail());

		emailService.sendSimpleMail(emailDetails);
		
		return savedOrderedTest;
	}

	@Override
	public EmailAudit notifyTestResult(TestResultBean testResultBean) throws InvalidInputException {
		EmailDetails emailDetails = new EmailDetails();

		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(testResultBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}
		OrderedTest orderedTest = orderedTestRepository
				.findByAppointmentBookingId(appointmentBookingOptional.get().getAppointmentBookingId())
				.orElseThrow(() -> new InvalidInputException(Constants.ORDERED_TEST_DOES_NOT_EXIST_MESSAGE));

		String emailBody = "Please find below the test result for your appointment: " + orderedTest.getAppointmentBooking().getAppointmentDetails() + " with the " + orderedTest.getAppointmentBooking().getPatientPracticeRegistration().getPractice().getPracticeName() + ": \n " + testResultBean.getTestResult();
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_ORDERED_TEST_RESULT);
		emailDetails.setMsgBody(emailBody);
		emailDetails.setRecipient(appointmentBookingOptional.get().getPatientPracticeRegistration().getPatient()
				.getUser().getUserEmail());

		emailService.sendSimpleMail(emailDetails);

		EmailAudit emailAudit = new EmailAudit();
		EmailAuditType emailAuditType = emailAuditTypeRepository
				.findByEmailAuditTypeName(Constants.EMAIL_AUDIT_TYPE_TEST_RESULT)
				.orElseThrow(() -> new InvalidInputException(Constants.EMAIL_AUDIT_TYPE_DOES_NOT_EXIST));
		emailAudit.setEmailAuditType(emailAuditType);
		User emailSenderUser = userRepository.findByUserEmail(emailSender)
				.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE));
		emailAudit.setEmailSender(emailSenderUser);
		emailAudit.setEmailReceiver(
				appointmentBookingOptional.get().getPatientPracticeRegistration().getPatient().getUser());
		emailAudit.setEmailSubject(orderedTest.getOrderedTestDetails());
		emailAudit.setEmailMessage(testResultBean.getTestResult());

		return emailAuditRepository.save(emailAudit);
	}

}
