package com.uos.electronic.healthcare.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponseBean {
	
	private String code;
	private String message;
	private String status;

}
