package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.Prescription;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientPrescriptionBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.PrescriptionRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public Prescription fetchPrescriptionDetails(int appointmentBookingId) {
		return prescriptionRepository.findByAppointmentBookingId(appointmentBookingId).orElse(null);
	}

	@Override
	public Prescription uploadPatientPrescription(PatientPrescriptionBean patientPrescriptionBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		if (prescriptionRepository.findByAppointmentBookingId(patientPrescriptionBean.getAppointmentBookingId()).isPresent()) {
			throw new DuplicateEntryException(Constants.APPOINTMENT_PRESCRIPTION_ALREADY_EXIST);
		}
		Prescription prescription = new Prescription();

		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(patientPrescriptionBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}

		prescription.setAppointmentBooking(appointmentBookingOptional.get());
		prescription.setPrescriptionDetails(patientPrescriptionBean.getPrescriptionDetails());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date prescriptionDate = null;
		try {
			prescriptionDate = simpleDateFormat.parse(patientPrescriptionBean.getPrescriptionDate());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		prescription.setPrescriptionDate(prescriptionDate);
		
		Prescription savedPrescription = prescriptionRepository.save(prescription);
		
		String emailBody = "Please find below the uploaded prescription for your appointment: " + savedPrescription.getAppointmentBooking().getAppointmentDetails() + " with the " + savedPrescription.getAppointmentBooking().getPatientPracticeRegistration().getPractice().getPracticeName() + ": \n " + savedPrescription.getPrescriptionDetails();
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_PRESCRIPTION_UPLOAD);
		emailDetails.setMsgBody(emailBody);
		emailDetails.setRecipient(appointmentBookingOptional.get().getPatientPracticeRegistration().getPatient()
				.getUser().getUserEmail());
		emailService.sendSimpleMail(emailDetails);
		
		return savedPrescription;
		
		

	}

}
