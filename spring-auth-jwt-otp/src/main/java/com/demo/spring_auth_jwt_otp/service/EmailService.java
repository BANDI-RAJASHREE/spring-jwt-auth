package com.demo.spring_auth_jwt_otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void SendOtp(String toEmail, String otp) {
        logger.info("Preparing to send OTP to email: {}", toEmail);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + ". It expires in 5 minutes.");
            mailSender.send(message);
            logger.info("OTP sent successfully to {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send OTP to {}: {}", toEmail, e.getMessage());
        }
    }
}
