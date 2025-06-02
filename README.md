# RESTful API – JWT Security with OTP Verification & Email Service

This project implements a secure Spring Boot-based REST API with:

- OTP verification for login
- JWT-based authentication
- Email service to send OTPs

Add loggers using SLF4J and Documenting REST API Using OpenAPI 3.0

---

##  Flow

1. User sends email to initiate login
2. OTP is generated and sent to the user's email
3. User verifies OTP via API
4. JWT token is issued on successful verification
5. Protected APIs are accessed using the JWT token

---

## Endpoints

### GET `/api/auth/send-otp?email={email}`

- **Description**: Sends OTP to the provided email

### GET `/api/auth/verify-otp?email={email}&otp={otp}`
- **Description**: Verifies the OTP and issues a JWT

### GET `/api/test/protected`
- **Description**: A sample protected endpoint
- **Headers**: Authorization: Bearer <JWT>
