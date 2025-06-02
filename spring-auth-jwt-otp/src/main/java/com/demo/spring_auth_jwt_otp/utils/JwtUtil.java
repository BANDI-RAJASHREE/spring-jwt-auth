package com.demo.spring_auth_jwt_otp.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    public String generateToken(String email) {
        logger.info("Generating JWT token for email: {}", email);
        try {
            String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
            logger.debug("Generated JWT token: {}", token);
            return token;
        } catch (Exception e) {
            logger.error("Error generating JWT token for email {}: {}", email, e.getMessage());
            throw e;
        }
    }

    public String extractUsername(String token) {
        logger.info("Extracting username from token");
        try {
            String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            logger.debug("Extracted username: {}", username);
            return username;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired: {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error extracting username from token: {}", e.getMessage());
            throw e;
        }
    }

}
