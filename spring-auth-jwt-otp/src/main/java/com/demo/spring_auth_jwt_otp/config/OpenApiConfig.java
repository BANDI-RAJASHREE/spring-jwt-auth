package com.demo.spring_auth_jwt_otp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springpOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Spring Auth OTP API")
            .description("API for OTP authentication with JWT")
            .version("v1.0"));
    }
}

