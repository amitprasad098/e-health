package com.uos.electronic.healthcare.service.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;
import com.uos.electronic.healthcare.entity.Pharmacy;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.RequestAlreadyExistException;
import com.uos.electronic.healthcare.model.UserPracticeRegistrationBean;
import com.uos.electronic.healthcare.repository.PatientPracticeRegistrationRepository;
import com.uos.electronic.healthcare.repository.PatientRepository;
import com.uos.electronic.healthcare.repository.PharmacyRepository;
import com.uos.electronic.healthcare.repository.PracticeRepository;
import com.uos.electronic.healthcare.service.EmailService;
import com.uos.electronic.healthcare.service.PatientPracticeRegistrationService;
import com.uos.electronic.healthcare.model.PatientPracticeResponseBean;

@Service
public class PatientPracticeRegistrationServiceImpl implements PatientPracticeRegistrationService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PracticeRepository practiceRepository;

	@Autowired
	private PatientPracticeRegistrationRepository patientPracticeRegistrationRepository;

	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public List<Practice> fetchPractices() {
		return practiceRepository.findAll();
	}

	@Override
	public List<PatientPracticeResponseBean> fetchPracticesByPatientId(int patientId) {
		List<PatientPracticeResponseBean> patientPracticeResponseBeans = new ArrayList<>();
		List<Practice> practices = practiceRepository.findAll();
		practices.forEach(practice -> {
			PatientPracticeResponseBean patientPracticeResponseBean = PatientPracticeResponseBean.builder()
					.practiceId(practice.getPracticeId()).practiceName(practice.getPracticeName())
					.practiceDescription(practice.getPracticeDescription())
					.practiceAddress(practice.getPracticeAddress())
					.practiceContactDetails(practice.getPracticeContactDetails())
					.practiceImageUrl(practice.getPracticeImageUrl()).build();

			Optional<PatientPracticeRegistration> patientPracticeRegistrationOptional = patientPracticeRegistrationRepository
					.findByPatientIdAndPracticeId(patientId, practice.getPracticeId());
			if (!patientPracticeRegistrationOptional.isPresent()) {
				patientPracticeResponseBean.setPatientRegistrationStatus(Constants.UNAVAILABLE_STATUS);
			} else {
				patientPracticeResponseBean.setPatientRegistrationStatus(
						patientPracticeRegistrationOptional.get().getRegistrationStatus());
			}
			patientPracticeResponseBeans.add(patientPracticeResponseBean);
		});
		return patientPracticeResponseBeans;
	}

	@Override
	public void registerUserToPractice(UserPracticeRegistrationBean userPracticeRegistrationBean)
			throws InvalidInputException, RequestAlreadyExistException {

		Optional<PatientPracticeRegistration> patientPracticeRegistrationOptional = patientPracticeRegistrationRepository
				.findByPatientIdAndPracticeId(userPracticeRegistrationBean.getPatientId(),
						userPracticeRegistrationBean.getPracticeId());
		if (patientPracticeRegistrationOptional.isPresent() && !patientPracticeRegistrationOptional.get()
				.getRegistrationStatus().equals(Constants.DECLINED_STATUS)) {
			throw new RequestAlreadyExistException(Constants.PATIENT_ALREADY_REGISTED);

		}
		PatientPracticeRegistration patientPracticeRegistration;
		if (patientPracticeRegistrationOptional.isPresent()) {
			patientPracticeRegistration = patientPracticeRegistrationOptional.get();
		} else {
			patientPracticeRegistration = new PatientPracticeRegistration();
		}
		patientPracticeRegistration
				.setPractice(practiceRepository.findById(userPracticeRegistrationBean.getPracticeId())
						.orElseThrow(() -> new InvalidInputException(Constants.PRACTICE_DOES_NOT_EXIST_MESSAGE)));
		patientPracticeRegistration
				.setPatient(patientRepository.findByPatientId(userPracticeRegistrationBean.getPatientId())
						.orElseThrow(() -> new InvalidInputException(Constants.USER_DOES_NOT_EXIST_MESSAGE)));
		patientPracticeRegistration.setRegistrationStatus(Constants.PENDING_STATUS);
		patientPracticeRegistration.setRegistrationDate(
				Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		patientPracticeRegistrationRepository.save(patientPracticeRegistration);
		
		String emailBody = "Your request to register to " + patientPracticeRegistration.getPractice().getPracticeName() + " has been received. Please wait for the approval";
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setRecipient(patientPracticeRegistration.getPatient().getUser().getUserEmail());
		emailDetails.setSubject(Constants.EMAIL_SUBJECT_REGISTER_TO_PRACTICE);
		emailDetails.setMsgBody(emailBody);
		
	}

	@Override
	public String fetchRegistrationRequestStatus(int patientId, int practiceId) {
		Optional<PatientPracticeRegistration> userPracticeRegistrationOptional = patientPracticeRegistrationRepository
				.findByPatientIdAndPracticeId(patientId, practiceId);
		if (!userPracticeRegistrationOptional.isPresent()) {
			return Constants.EMPTY_STATUS;
		} else {
			return userPracticeRegistrationOptional.get().getRegistrationStatus();
		}
	}

	@Override
	public List<Pharmacy> fetchPharmaciesByCity(String city) {
		return pharmacyRepository.findByCity(city);
	}

}
