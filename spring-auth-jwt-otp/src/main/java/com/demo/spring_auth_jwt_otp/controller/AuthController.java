package com.demo.spring_auth_jwt_otp.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring_auth_jwt_otp.service.EmailService;
import com.demo.spring_auth_jwt_otp.service.OtpService;
import com.demo.spring_auth_jwt_otp.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger=LoggerFactory.getLogger(AuthController.class);
	@Autowired 
	private OtpService otpService;
    @Autowired
    private EmailService emailService;
    @Autowired 
    private JwtUtil jwtUtil;
    
    @PostMapping("/send-otp")
    public ResponseEntity<?>sendOtp(@RequestParam String email)
    {
        logger.info("Received OTP request for email: {}", email);
    	String otp=otpService.generateOtp(email);
    	emailService.SendOtp(email, otp);
        logger.info("OTP successfully generated and sent to {}", email);
    	return ResponseEntity.ok("Otp is successfully sent to email");
    	
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<?>verifyOtp(@RequestParam String email,@RequestParam String otp)
    {
        logger.info("Attempting OTP verification for email: {} ", email);
    	boolean isValid=otpService.validateOtp(email, otp);
    	if (!isValid) { 
    	    logger.warn("Invalid or expired OTP for email: {}", email);
    	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
    	}
    	String token = jwtUtil.generateToken(email);
    	return ResponseEntity.ok(Collections.singletonMap("token", token));

    }
	
}
