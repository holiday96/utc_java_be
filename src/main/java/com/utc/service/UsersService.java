package com.utc.service;

import com.utc.contants.ResponseStatus;
import com.utc.contants.UserStatus;
import com.utc.dto.request.UserRequest;
import com.utc.dto.response.ApiResponse;
import com.utc.dto.response.UserResponse;
import com.utc.model.Users;
import com.utc.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 25.5.2024
 * Description :
 */
@Service
public class UsersService {

    private static final Logger log = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<UserResponse> register(UserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Users user = Users.builder()
                .roleId(request.getRoleId())
                .fullName(request.getFullName())
                .avatar(request.getAvatar())
                .address(request.getAddress())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(encodedPassword)
                .status(UserStatus.ACTIVE.code)
                .modifiedBy("ADMIN")
                .build();

        Users savedUser = userRepository.save(user);
        UserResponse userResponse = UserResponse.builder()
                .id(savedUser.getId())
                .build();

        return new ApiResponse<>(
                ResponseStatus.SUCCESS.code,
                ResponseStatus.SUCCESS.msg,
                userResponse
        );
    }
}
