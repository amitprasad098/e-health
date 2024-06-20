package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.Pharmacy;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.RequestAlreadyExistException;
import com.uos.electronic.healthcare.model.PatientPracticeResponseBean;
import com.uos.electronic.healthcare.model.UserPracticeRegistrationBean;

public interface PatientPracticeRegistrationService {
	
	List<Practice> fetchPractices();
	
	List<PatientPracticeResponseBean> fetchPracticesByPatientId(int patientId);
	
	void registerUserToPractice(UserPracticeRegistrationBean userPracticeRegistrationBean) throws RequestAlreadyExistException, InvalidInputException;
	
	String fetchRegistrationRequestStatus(int patientId, int practiceId);
	
	List<Pharmacy> fetchPharmaciesByCity(String city);

}
