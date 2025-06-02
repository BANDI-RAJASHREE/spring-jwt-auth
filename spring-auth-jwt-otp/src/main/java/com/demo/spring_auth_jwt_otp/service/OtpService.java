package com.demo.spring_auth_jwt_otp.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
	private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, new OtpEntry(otp, System.currentTimeMillis() + 5 * 60 * 1000));
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        OtpEntry entry = otpStore.get(email);
        if (entry == null || System.currentTimeMillis() > entry.expiryTime)
            return false;
        return entry.otp.equals(otp);
    }

    private static class OtpEntry {
        String otp;
        long expiryTime;
        OtpEntry(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }

}
