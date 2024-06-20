package com.uos.electronic.healthcare.service;

import com.uos.electronic.healthcare.entity.EmailAudit;
import com.uos.electronic.healthcare.entity.OrderedTest;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.model.OrderedTestBean;
import com.uos.electronic.healthcare.model.TestResultBean;

public interface OrderedTestService {

	OrderedTest fetchOrderedTestDetails(int appointmentBookingId);

	OrderedTest uploadOrderedTest(OrderedTestBean orderedTestBean)
			throws InvalidInputException, DateExtractionException, DuplicateEntryException;

	EmailAudit notifyTestResult(TestResultBean testResultBean) throws InvalidInputException;

}
