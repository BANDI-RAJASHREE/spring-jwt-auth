package com.demo.spring_auth_jwt_otp.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring_auth_jwt_otp.service.EmailService;
import com.demo.spring_auth_jwt_otp.service.OtpService;
import com.demo.spring_auth_jwt_otp.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints for sending and verifying OTP. Use `/send-otp` first to get an OTP, then `/verify-otp` to verify it."
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(
            summary = "Send OTP",
            description = "Sends a 6-digit OTP to the given email address",
            operationId = "1_sendOtp",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid email")
            }
    )
    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        logger.info("Received OTP request for email: {}", email);
        String otp = otpService.generateOtp(email);
        emailService.SendOtp(email, otp);
        logger.info("OTP successfully generated and sent to {}", email);
        return ResponseEntity.ok("Otp is successfully sent to email");
    }

    @Operation(
            summary = "Verify OTP",
            description = "Verifies the OTP provided for the given email and returns a JWT token if valid",
            operationId = "2_verifyOtp",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OTP verified successfully, JWT token returned"),
                    @ApiResponse(responseCode = "401", description = "Invalid or expired OTP")
            }
    )
    @GetMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        logger.info("Attempting OTP verification for email: {} ", email);
        boolean isValid = otpService.validateOtp(email, otp);
        if (!isValid) {
            logger.warn("Invalid or expired OTP for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

}
