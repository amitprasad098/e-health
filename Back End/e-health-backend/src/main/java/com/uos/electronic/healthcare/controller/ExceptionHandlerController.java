package com.uos.electronic.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.uos.electronic.healthcare.constant.Constants;
import com.uos.electronic.healthcare.exception.DateExtractionException;
import com.uos.electronic.healthcare.exception.DuplicateEntryException;
import com.uos.electronic.healthcare.exception.InvalidInputException;
import com.uos.electronic.healthcare.exception.RequestAlreadyExistException;
import com.uos.electronic.healthcare.model.ExceptionResponseBean;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponseBean> handleException(Exception exception) {
		ExceptionResponseBean exceptionResponseBean = ExceptionResponseBean.builder().code("Exception code")
				.message(exception.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST.name()).build();
		return new ResponseEntity<>(exceptionResponseBean, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponseBean> handleInvalidInputException(InvalidInputException invalidInputException) {
		ExceptionResponseBean exceptionResponseBean = ExceptionResponseBean.builder().code(Constants.INVALID_INPUT_EXCEPTION_CODE)
				.message(invalidInputException.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST.name()).build();
		return new ResponseEntity<>(exceptionResponseBean, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(DateExtractionException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponseBean> handleDateExtractionException(DateExtractionException dateExtractionException) {
		ExceptionResponseBean exceptionResponseBean = ExceptionResponseBean.builder().code(Constants.DATE_EXTRACTION_EXCEPTION_CODE)
				.message(dateExtractionException.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST.name()).build();
		return new ResponseEntity<>(exceptionResponseBean, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(RequestAlreadyExistException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponseBean> handleRequestAlreadyExistException(RequestAlreadyExistException requestAlreadyExistException) {
		ExceptionResponseBean exceptionResponseBean = ExceptionResponseBean.builder().code(Constants.REQUEST_ALREADY_EXIST_EXCEPTION_CODE)
				.message(requestAlreadyExistException.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST.name()).build();
		return new ResponseEntity<>(exceptionResponseBean, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponseBean> handleDuplicateEntryException(DuplicateEntryException duplicateEntryException) {
		ExceptionResponseBean exceptionResponseBean = ExceptionResponseBean.builder().code(Constants.DUPLICATE_ENTRY_EXCEPTION_CODE)
				.message(duplicateEntryException.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST.name()).build();
		return new ResponseEntity<>(exceptionResponseBean, HttpStatus.BAD_REQUEST);

	}
}
