package com.utc.controllers;

import com.utc.payload.request.LoginRequest;
import com.utc.payload.request.UserSignupRequest;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<RestApiResponse> registerUser(@Valid @RequestBody UserSignupRequest userSignUpRequest) {
        return authService.registerUser(userSignUpRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<RestApiResponse> logoutUser() {
        return authService.logoutUser();
    }
}
