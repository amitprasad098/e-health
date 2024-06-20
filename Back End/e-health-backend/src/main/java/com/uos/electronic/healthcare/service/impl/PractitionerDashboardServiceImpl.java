package com.uos.electronic.healthcare.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.Doctor;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.AlternativeAppointmentBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.DoctorRepository;
import com.uos.electronic.healthcare.repository.OrderedTestRepository;
import com.uos.electronic.healthcare.repository.PracticeRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.PractitionerDashboardService;

@Service
public class PractitionerDashboardServiceImpl implements PractitionerDashboardService {

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PracticeRepository practiceRepository;
	
	@Autowired
	private OrderedTestRepository orderedTestRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public List<AppointmentBooking> fetchAppointmentRequestsByStatus(int practitionerId, String appointmentStatus) {
		return appointmentBookingRepository.findByPractitionerIdAndAppointmentStatus(practitionerId, appointmentStatus);
	}

	@Override
	public void approveAppointmentRequests(int appointmentBookingId, int doctorId) throws InvalidInputException {
		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(appointmentBookingId);
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}
		AppointmentBooking appointmentBooking = appointmentBookingOptional.get();
		appointmentBooking.setAppointmentStatus(Constants.APPROVED_STATUS);
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new InvalidInputException(Constants.DOCTOR_DOES_NOT_EXIST_MESSAGE));
		appointmentBooking.setDoctor(doctor);
		appointmentBookingRepository.save(appointmentBooking);
		
		String emailBody = "Your appointment with the " + appointmentBooking.getPatientPracticeRegistration().getPractice().getPracticeName() + " for the appoint details: " + appointmentBooking.getAppointmentDetails() + " has been approved. You can visit the practice on your selected date.";
		emailService.sendSimpleMail(generateEmailDetails(appointmentBooking.getPatientPracticeRegistration().getPatient().getUser().getUserEmail(), Constants.EMAIL_SUBJECT_APPOINTMENT_APPROVED, emailBody));
	}

	@Override
	public void declineAppointmentRequests(int appointmentBookingId) throws InvalidInputException {
		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(appointmentBookingId);
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}
		AppointmentBooking appointmentBooking = appointmentBookingOptional.get();
		appointmentBooking.setAppointmentStatus(Constants.DECLINED_STATUS);
		appointmentBookingRepository.save(appointmentBooking);
		
		String emailBody = "Your appointment with the " + appointmentBooking.getPatientPracticeRegistration().getPractice().getPracticeName() + " for the appoint details: " + appointmentBooking.getAppointmentDetails() + " has been declined. You can opt to book another appointment.";
		emailService.sendSimpleMail(generateEmailDetails(appointmentBooking.getPatientPracticeRegistration().getPatient().getUser().getUserEmail(), Constants.EMAIL_SUBJECT_APPOINTMENT_DECLINED, emailBody));
	}

	@Override
	public void offerAlternateAppointment(AlternativeAppointmentBean alternativeAppointmentBean)
			throws InvalidInputException {
		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(alternativeAppointmentBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}
		AppointmentBooking appointmentBooking = appointmentBookingOptional.get();
		appointmentBooking.setPractitionerFeedback(alternativeAppointmentBean.getAlternativeMessage());
		appointmentBooking.setAppointmentStatus(Constants.ALTERNATE_STATUS);
		appointmentBookingRepository.save(appointmentBooking);
		
		String emailBody = "Your appointment with the " + appointmentBooking.getPatientPracticeRegistration().getPractice().getPracticeName() + " for the appoint details: " + appointmentBooking.getAppointmentDetails() + " has an alternative suggestion. Please view it on your website dashboard";
		emailService.sendSimpleMail(generateEmailDetails(appointmentBooking.getPatientPracticeRegistration().getPatient().getUser().getUserEmail(), Constants.EMAIL_SUBJECT_APPOINTMENT_ALTERNATE, emailBody));
	}

	@Override
	public List<Doctor> fetchDoctorsByPractice(int practiceId) throws InvalidInputException {
		Practice practice = practiceRepository.findById(practiceId)
				.orElseThrow(() -> new InvalidInputException(Constants.PRACTICE_DOES_NOT_EXIST_MESSAGE));
		return doctorRepository.findByPracticeId(practice.getPracticeId());
	}

	@Override
	public List<OrderedTest> fetchTestAppointmentsByPractice(int practiceId) {
		return orderedTestRepository.findByPracticeId(practiceId);
	}
	
	private EmailDetails generateEmailDetails(String emailRecipient, String emailSubject, String emailBody) {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(emailRecipient);
		emailDetails.setSubject(emailSubject);
		emailDetails.setMsgBody(emailBody);
		
		return emailDetails;
	}

}
