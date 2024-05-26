package com.utc.controller;

import com.utc.dto.request.UserRequest;
import com.utc.dto.response.ApiResponse;
import com.utc.dto.response.UserResponse;
import com.utc.model.Users;
import com.utc.repository.UsersRepository;
import com.utc.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 25.5.2024
 * Description :
 */
@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UsersService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request) {
        return userService.register(request);
    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> ResponseEntity.ok().body(user))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/users/{id}")
//    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(userDetails.getUsername());
//                    user.setPassword(userDetails.getPassword());
//                    return ResponseEntity.ok().body(userRepository.save(user));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    userRepository.delete(user);
//                    return ResponseEntity.ok().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
}
