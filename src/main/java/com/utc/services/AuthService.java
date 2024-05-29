package com.utc.services;

import com.utc.payload.request.LoginRequest;
import com.utc.payload.request.UserSignupRequest;
import org.springframework.http.ResponseEntity;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(UserSignupRequest userSignUpRequest);

    ResponseEntity<String> logoutUser();
}
