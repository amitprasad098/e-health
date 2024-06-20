package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.MedicalHistory;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.MedicalHistoryBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.MedicalHistoryRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.MedicalHistoryService;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public List<MedicalHistory> fetchMedicalHistoryDetails(int appointmentBookingId) throws InvalidInputException {
		AppointmentBooking appointmentBooking = appointmentBookingRepository.findById(appointmentBookingId)
				.orElseThrow(() -> new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE));
		return medicalHistoryRepository.findByPatientPracticeRegistraionId(
				appointmentBooking.getPatientPracticeRegistration().getPatientPracticeRegistrationId());
	}

	@Override
	public MedicalHistory uploadMedicalHistory(MedicalHistoryBean medicalHistoryBean)
			throws InvalidInputException, DateExtractionException {
		MedicalHistory medicalHistory = new MedicalHistory();

		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(medicalHistoryBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}

		medicalHistory.setAppointmentBooking(appointmentBookingOptional.get());
		medicalHistory.setMedicalHistoryDetails(medicalHistoryBean.getMedicalHistoryDetails());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date medicalHistoryDate = null;
		try {
			medicalHistoryDate = simpleDateFormat.parse(medicalHistoryBean.getMedicalHistoryDate());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		medicalHistory.setMedicalHistoryDate(medicalHistoryDate);
		MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(medicalHistory);
		
		String emailBody = "Please find below the updated medical history for your appointment: " + savedMedicalHistory.getAppointmentBooking().getAppointmentDetails() + " with the " + savedMedicalHistory.getAppointmentBooking().getPatientPracticeRegistration().getPractice().getPracticeName() + ": \n " + savedMedicalHistory.getMedicalHistoryDetails();
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(savedMedicalHistory.getAppointmentBooking().getPatientPracticeRegistration().getPatient().getUser().getUserEmail());
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_MEDICAL_HISTORY_UPLOAD);
		emailDetails.setMsgBody(emailBody);
		emailService.sendSimpleMail(emailDetails);
		
		return savedMedicalHistory;
	}

}
