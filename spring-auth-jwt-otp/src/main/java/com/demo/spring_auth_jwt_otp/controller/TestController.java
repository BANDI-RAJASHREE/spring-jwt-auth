package com.demo.spring_auth_jwt_otp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/test")
public class TestController {
	 @GetMapping("/protected")
	    public ResponseEntity<?> protectedRoute(HttpServletRequest request) {
	        String email = (String) request.getAttribute("email");
	        return ResponseEntity.ok("Hello, " + email);
	    }

}
