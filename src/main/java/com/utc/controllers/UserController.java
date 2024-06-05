package com.utc.controllers;

import com.utc.payload.request.AddUserRequest;
import com.utc.payload.request.UpdateUserRequest;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestApiResponse> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        log.info("Request addUser: {}", addUserRequest);
        return userService.addUser(addUserRequest);
    }

    @PatchMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestApiResponse> updateUser(@PathVariable("user_id") Long userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("Request updateUser: {}", updateUserRequest);
        return userService.updateUser(userId, updateUserRequest);
    }
}
