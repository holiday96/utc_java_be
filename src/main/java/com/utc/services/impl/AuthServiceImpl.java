package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.ERole;
import com.utc.contants.UserStatus;
import com.utc.exception.ForbiddenException;
import com.utc.exception.ValidateException;
import com.utc.models.Role;
import com.utc.models.User;
import com.utc.payload.request.LoginRequest;
import com.utc.payload.request.UserSignupRequest;
import com.utc.payload.response.RestApiResponse;
import com.utc.payload.response.SigninResponse;
import com.utc.payload.response.UserInfoResponse;
import com.utc.repository.RoleRepository;
import com.utc.repository.UserRepository;
import com.utc.services.AuthService;
import com.utc.services.UserService;
import com.utc.utils.JwtUtils;
import com.utc.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<SigninResponse> authenticateUser(LoginRequest loginRequest) {
        UserInfoResponse userInfoResponse = userService.findByUsername(loginRequest.getUsername());
        if (Objects.equals(userInfoResponse.getStatus(), UserStatus.INACTIVE.code)) {
            throw new ForbiddenException(
                    MessageUtils.getProperty(messageSource, "user_inactive")
            );
        } else if (Objects.equals(userInfoResponse.getStatus(), UserStatus.BLOCKED.code)) {
            throw new ForbiddenException(
                    MessageUtils.getProperty(messageSource, "user_blocked")
            );
        } else {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new SigninResponse(
                            ApiStatus.SUCCESS.code,
                            ApiStatus.SUCCESS.toString().toLowerCase(),
                            userInfoResponse,
                            jwtCookie.toString()
                    ));
        }
    }

    @Override
    public ResponseEntity<RestApiResponse> registerUser(UserSignupRequest userSignUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userSignUpRequest.getUsername()))) {
            throw new ValidateException(
                    ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                    MessageUtils.getProperty(messageSource, "username_already_exist")
            );
        }

        // Create new user's account
        User user = User.builder()
                .username(userSignUpRequest.getUsername())
                .password(encoder.encode(userSignUpRequest.getPassword()))
                .status(UserStatus.ACTIVE.code)
                .modifiedBy(ERole.ROLE_USER.name())
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ValidateException(
                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                        MessageUtils.getProperty(messageSource, "role_not_found")
                ));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                ));
    }
}
