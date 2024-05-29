package com.utc.services;

import com.utc.payload.request.LoginRequest;
import com.utc.payload.request.UserSignupRequest;
import com.utc.payload.response.RestApiResponse;
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

    ResponseEntity<RestApiResponse> registerUser(UserSignupRequest userSignUpRequest);

    ResponseEntity<RestApiResponse> logoutUser();
}
