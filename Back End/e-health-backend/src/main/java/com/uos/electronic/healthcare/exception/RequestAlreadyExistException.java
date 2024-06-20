package com.uos.electronic.healthcare.exception;

public class RequestAlreadyExistException extends Exception {

	private static final long serialVersionUID = -2821547902117239058L;
	
	public RequestAlreadyExistException(String message) {
		super(message);
	}

}
