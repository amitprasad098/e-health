package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.Practitioner;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.NoAvailableStaffException;
import com.uos.electronic.healthcare.model.AppointmentBookingBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.PatientPracticeRegistrationRepository;
import com.uos.electronic.healthcare.repository.PractitionerRepository;
import com.uos.electronic.healthcare.service.AppointmentBookingService;
import com.uos.electronic.healthcare.service.EmailService;

@Service
public class AppointmentBookingServiceImpl implements AppointmentBookingService {

	@Autowired
	private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;
	
	@Autowired
	private PractitionerRepository practitionerRepository;

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public AppointmentBooking bookAnAppointment(AppointmentBookingBean appointmentBookingBean)
			throws InvalidInputException, DateExtractionException, NoAvailableStaffException {
		AppointmentBooking appointmentBooking = new AppointmentBooking();
		appointmentBooking.setPatientPracticeRegistration(patientPracticeRegistrationRepository
				.findByPatientIdAndPracticeId(appointmentBookingBean.getPatientId(),
						appointmentBookingBean.getPracticeId())
				.orElseThrow(() -> new InvalidInputException(Constants.PATIENT_PRACTICE_REGISTRATION_DOES_NOT_EXIST_MESSAGE)));
		appointmentBooking.setAppointmentDetails(appointmentBookingBean.getAppointmentDescription());
		appointmentBooking.setAppointmentStatus(Constants.PENDING_STATUS);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date appointmentSelectedDate = null;
		try {
			appointmentSelectedDate = simpleDateFormat.parse(appointmentBookingBean.getAppointmentDate());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		appointmentBooking.setAppointmentDate(appointmentSelectedDate);
		List<Practitioner> practitionerList = practitionerRepository.findByPracticeId(appointmentBookingBean.getPracticeId());
		if(practitionerList.isEmpty()) {
			throw new NoAvailableStaffException(Constants.PRACTITIONER_DOES_NOT_EXIST);
		}
		appointmentBooking.setPractitioner(practitionerList.get(Constants.FIRST_INDEX_ELEMENT));

		AppointmentBooking bookedAppointment =  appointmentBookingRepository.save(appointmentBooking);
		
		
		String emailBody = "You appointment booking request to " + bookedAppointment.getPatientPracticeRegistration().getPractice().getPracticeName() + " has been received. Please wait for the approval.";
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(bookedAppointment.getPatientPracticeRegistration().getPatient().getUser().getUserEmail());
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_APPOINTMENT_BOOKING);
		emailDetails.setMsgBody(emailBody);
		emailService.sendSimpleMail(emailDetails);
		
		return bookedAppointment;
	}

	@Override
	public List<AppointmentBooking> fetchAllPatientAppointments(int patientId) {
		return appointmentBookingRepository.findByPatientId(patientId);
	}

}
