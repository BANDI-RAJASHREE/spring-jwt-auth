package com.demo.spring_auth_jwt_otp.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, new OtpEntry(otp, System.currentTimeMillis() + 5 * 60 * 1000));
        logger.info("Generated OTP for {}: {}", email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        OtpEntry entry = otpStore.get(email);
        if (entry == null) {
            logger.warn("No OTP found for {}", email);
            return false;
        }
        if (System.currentTimeMillis() > entry.expiryTime) {
            logger.warn("OTP for {} has expired", email);
            return false;
        }
        boolean isValid = entry.otp.equals(otp);
        if (isValid) {
            logger.info("OTP validated successfully for {}", email);
            otpStore.remove(email);
        } else {
            logger.warn("Invalid OTP provided for {}", email);
        }
        return isValid;
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
