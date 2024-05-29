package com.utc.services.impl;

import com.utc.models.User;
import com.utc.payload.response.UserInfoResponse;
import com.utc.repository.UserRepository;
import com.utc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserInfoResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        List<String> roles = user.getRoles().stream()
                .map(e -> e.getName().name())
                .collect(Collectors.toList());

        return new UserInfoResponse(
                user.getId(),
                user.getFullName(),
                user.getAvatar(),
                user.getAddress(),
                user.getPhone(),
                user.getEmail(),
                user.getUsername(),
                user.getStatus(),
                roles
        );
    }
}
