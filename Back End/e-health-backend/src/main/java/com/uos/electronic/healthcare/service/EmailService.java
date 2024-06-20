package com.uos.electronic.healthcare.service;

import com.uos.electronic.healthcare.entity.EmailDetails;

public interface EmailService {

	String sendSimpleMail(EmailDetails details);

	String sendMailWithAttachment(EmailDetails details);
}
