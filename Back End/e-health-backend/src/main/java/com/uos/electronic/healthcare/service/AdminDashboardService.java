package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.PatientPracticeRegistration;
import com.uos.electronic.healthcare.entity.Practice;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.DoctorRegistrationBean;
import com.uos.electronic.healthcare.model.PracticeRegistrationBean;
import com.uos.electronic.healthcare.model.PractitionerRegistrationBean;
import com.uos.electronic.healthcare.model.UpdateUserPasswordBean;

public interface AdminDashboardService {
	
	List<PatientPracticeRegistration> fetchPendingRegistrationRequests();
	
	void approvePendingRegistrationRequests(int patientPracticeRegistrationId) throws InvalidInputException;
	
	void declinePendingRegistrationRequests(int patientPracticeRegistrationId) throws InvalidInputException;
	
	void addNewPractice(PracticeRegistrationBean practiceRegistrationBean);
	
	void addNewDoctor(DoctorRegistrationBean doctorRegistrationBean);
	
	List<Practice> fetchPracticesWithoutPractitioner();
	
	void addNewPractitioner(PractitionerRegistrationBean practitionerRegistrationBean);
	
	void updateUserPassword(UpdateUserPasswordBean updateUserPasswordBean) throws InvalidInputException;

}
