package com.utc.services.impl;

import com.utc.contants.ERole;
import com.utc.contants.ResponseStatus;
import com.utc.contants.UserStatus;
import com.utc.models.Role;
import com.utc.models.User;
import com.utc.payload.request.LoginRequest;
import com.utc.payload.request.UserSignupRequest;
import com.utc.payload.response.UserInfoResponse;
import com.utc.repository.RoleRepository;
import com.utc.repository.UserRepository;
import com.utc.services.AuthService;
import com.utc.services.UserService;
import com.utc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        UserInfoResponse userInfoResponse = userService.findByUsername(loginRequest.getUsername());
        if (Objects.equals(userInfoResponse.getStatus(), UserStatus.INACTIVE.code)) {
            return ResponseEntity.ok().body(UserStatus.INACTIVE);
        } else if (Objects.equals(userInfoResponse.getStatus(), UserStatus.BLOCKED.code)) {
            return ResponseEntity.ok().body(UserStatus.BLOCKED);
        } else {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(userInfoResponse);
        }
    }

    @Override
    public ResponseEntity<?> registerUser(UserSignupRequest userSignUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userSignUpRequest.getUsername()))) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userSignUpRequest.getEmail()))) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .fullName(userSignUpRequest.getFullName())
                .address(userSignUpRequest.getAddress())
                .phone(userSignUpRequest.getPhone())
                .email(userSignUpRequest.getEmail())
                .username(userSignUpRequest.getUsername())
                .password(encoder.encode(userSignUpRequest.getPassword()))
                .status(UserStatus.ACTIVE.code)
                .modifiedBy(ERole.ROLE_USER.name())
                .build();

        Set<String> strRoles = userSignUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(ResponseStatus.SUCCESS.msg);
    }

    @Override
    public ResponseEntity<String> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ResponseStatus.SUCCESS.msg);
    }
}
