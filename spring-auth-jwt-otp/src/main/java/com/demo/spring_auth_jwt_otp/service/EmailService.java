package com.demo.spring_auth_jwt_otp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void SendOtp(String toEmail,String otp)
	{
		SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(toEmail);
	        message.setSubject("Your OTP Code");
	        message.setText("Your OTP is: " + otp + ". It expires in 5 minutes.");
	        mailSender.send(message);
	}
	
	

}
