package com.uos.electronic.healthcare.service;

import com.uos.electronic.healthcare.entity.EmailAudit;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.UpdatePatientDataBean;

public interface DataRegulationService {

	void viewUserData(int userId) throws InvalidInputException;

	void updateUserData(UpdatePatientDataBean updatePatientDataBean) throws InvalidInputException;
	
	void deleteUserData(int userId) throws InvalidInputException;

}
