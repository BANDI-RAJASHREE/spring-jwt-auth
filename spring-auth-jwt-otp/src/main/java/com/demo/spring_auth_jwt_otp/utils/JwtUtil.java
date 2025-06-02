package com.demo.spring_auth_jwt_otp.utils;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secret;
	
    @Value("${jwt.expiration}")
    private long expirationMs;
    
    public String generateToken(String email)
    {
    	return Jwts.builder()
    			.setSubject(email)
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(System.currentTimeMillis() + expirationMs))
    			.signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    public String extractUsername(String token)
    {
    	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
	
	

}
