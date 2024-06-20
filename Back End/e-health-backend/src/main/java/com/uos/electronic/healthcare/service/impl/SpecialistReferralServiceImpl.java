package com.uos.electronic.healthcare.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.AppointmentBooking;
import com.uos.electronic.healthcare.entity.Doctor;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.SpecialistReferral;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.SpecialistReferralBean;
import com.uos.electronic.healthcare.repository.AppointmentBookingRepository;
import com.uos.electronic.healthcare.repository.DoctorRepository;
import com.uos.electronic.healthcare.repository.SpecialistReferralRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.SpecialistReferralService;

@Service
public class SpecialistReferralServiceImpl implements SpecialistReferralService {

	@Autowired
	private AppointmentBookingRepository appointmentBookingRepository;

	@Autowired
	private SpecialistReferralRepository specialistReferralRepository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public SpecialistReferral fetchSpecialistReferralDetails(int appointmentBookingId) {
		return specialistReferralRepository.findByAppointmentBookingId(appointmentBookingId).orElse(null);
	}

	@Override
	public SpecialistReferral uploadSpecialistReferral(SpecialistReferralBean specialistReferralBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException {
		if(specialistReferralRepository.findByAppointmentBookingId(specialistReferralBean.getAppointmentBookingId()).isPresent()) {
			throw new DuplicateEntryException(Constants.REFERRAL_ALREADY_EXIST);
		}
		SpecialistReferral specialistReferral = new SpecialistReferral();
		Optional<AppointmentBooking> appointmentBookingOptional = appointmentBookingRepository
				.findById(specialistReferralBean.getAppointmentBookingId());
		if (!appointmentBookingOptional.isPresent()) {
			throw new InvalidInputException(Constants.APPOINTMENT_DOES_NOT_EXIST_MESSAGE);
		}
		Optional<Doctor> doctorOptional = doctorRepository.findById(specialistReferralBean.getReferredDoctorId());
		if (!doctorOptional.isPresent()) {
			throw new InvalidInputException(Constants.REFERRED_DOCTOR_DOES_NOT_EXIST_MESSAGE);
		}
		specialistReferral.setAppointmentBooking(appointmentBookingOptional.get());
		specialistReferral.setReferredDoctor(doctorOptional.get());
		String specialistReferralDetails = specialistReferralBean.getSpecialistReferralDetails() + " Referred doctor: " + specialistReferral.getReferredDoctor().getUser().getUserFullName() + " from the practice: " + specialistReferral.getReferredDoctor().getPractice().getPracticeName();
		specialistReferral.setSpecialistReferralDetails(specialistReferralDetails);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date specialReferralDate = null;
		try {
			specialReferralDate = simpleDateFormat.parse(specialistReferralBean.getSpecialistReferralDate());
		} catch (ParseException parseException) {
			throw new DateExtractionException(Constants.DATE_EXTRACTION_EXCEPTION_MESSAGE);
		}
		specialistReferral.setSpecialistReferralDate(specialReferralDate);

		SpecialistReferral saveSpecialistReferral = specialistReferralRepository.save(specialistReferral);
		
		String emailBody = "Please find below the specialist referral details for your appointment: " + saveSpecialistReferral.getAppointmentBooking().getAppointmentDetails() + " with the " + saveSpecialistReferral.getAppointmentBooking().getPatientPracticeRegistration().getPractice().getPracticeName() + ": \n " + saveSpecialistReferral.getSpecialistReferralDetails();
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_SPECIALIST_REFERRAL);
		emailDetails.setMsgBody(emailBody);
		emailDetails.setRecipient(appointmentBookingOptional.get().getPatientPracticeRegistration().getPatient()
				.getUser().getUserEmail());
		emailService.sendSimpleMail(emailDetails);
		
		return saveSpecialistReferral;
	}

}
