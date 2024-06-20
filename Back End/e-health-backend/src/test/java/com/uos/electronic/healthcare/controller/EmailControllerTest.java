package com.uos.electronic.healthcare.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.uos.electronic.healthcare.entity.EmailDetails;
import com.uos.electronic.healthcare.service.EmailService;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @Test
    void testSendMail() {
        // Setup mock data
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("test@example.com");
        emailDetails.setSubject("Test Subject");
        emailDetails.setMsgBody("Test Body");

        // Mock the service method
        when(emailService.sendSimpleMail(emailDetails)).thenReturn("Mail Sent Successfully");

        // Call the controller method and assert the response
        ResponseEntity<String> response = emailController.sendMail(emailDetails);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mail Sent Successfully", response.getBody());
    }

    @Test
    void testSendMailWithAttachment() {
        // Setup mock data
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("test@example.com");
        emailDetails.setSubject("Test Subject with Attachment");
        emailDetails.setMsgBody("Test Body with Attachment");
        emailDetails.setAttachment("path/to/attachment");

        // Mock the service method
        when(emailService.sendMailWithAttachment(emailDetails)).thenReturn("Mail Sent Successfully with Attachment");

        // Call the controller method and assert the response
        ResponseEntity<String> response = emailController.sendMailWithAttachment(emailDetails);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mail Sent Successfully with Attachment", response.getBody());
    }
}
