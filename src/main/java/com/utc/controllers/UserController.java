package com.utc.controllers;

import com.utc.payload.request.AddUserRequest;
import com.utc.payload.request.UpdateUserRequest;
import com.utc.payload.response.GetAllUserResponse;
import com.utc.payload.response.GetUserResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetAllUserResponse> getUserList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        log.info("Request getUserList: page={}, size={}", page, size);
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/info/{user_id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("user_id") @Positive Long userId) {
        log.info("Request getUserById: user_id={}", userId);
        return userService.getUserById(userId);
    }
}
