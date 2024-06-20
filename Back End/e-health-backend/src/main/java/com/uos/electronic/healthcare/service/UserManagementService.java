package com.uos.electronic.healthcare.service;

import java.util.List;

import com.uos.electronic.healthcare.entity.Patient;
import com.uos.electronic.healthcare.entity.UserType;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.PatientRegistrationBean;
import com.uos.electronic.healthcare.model.UserSignInBean;

public interface UserManagementService {
	
	List<UserType> fetchUserTypes();
	
	Patient registerPatient(PatientRegistrationBean patientRegistrationBean) throws DateExtractionException;
	
	Object signInUser(UserSignInBean userSignInBean) throws InvalidInputException;
	
	void forgotPasswordManagement(String userEmail) throws InvalidInputException;
	
}
