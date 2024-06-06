package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.ERole;
import com.utc.contants.UserStatus;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.models.Role;
import com.utc.models.User;
import com.utc.payload.request.AddUserRequest;
import com.utc.payload.request.UpdateUserRequest;
import com.utc.payload.response.*;
import com.utc.repository.RoleRepository;
import com.utc.repository.UserRepository;
import com.utc.services.UserService;
import com.utc.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private PasswordEncoder encoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    public ResponseEntity<RestApiResponse> addUser(AddUserRequest addUserRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(addUserRequest.getUsername()))) {
            throw new ValidateException(
                    ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                    MessageUtils.getProperty(messageSource, "username_already_exist")
            );
        }

        if (Boolean.TRUE.equals(userRepository.existsByPhone(addUserRequest.getPhone()))) {
            throw new ValidateException(
                    ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                    MessageUtils.getProperty(messageSource, "phone_already_exist")
            );
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(addUserRequest.getEmail()))) {
            throw new ValidateException(
                    ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                    MessageUtils.getProperty(messageSource, "email_already_exist")
            );
        }

        // Create new user's account
        User user = User.builder()
                .fullName(addUserRequest.getFullName())
                .address(addUserRequest.getAddress())
                .phone(addUserRequest.getPhone())
                .email(addUserRequest.getEmail())
                .username(addUserRequest.getUsername())
                .password(encoder.encode(addUserRequest.getPassword()))
                .status(UserStatus.ACTIVE.code)
                .modifiedBy(ERole.ROLE_USER.name())
                .build();

        Set<String> strRoles = addUserRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ValidateException(
                            ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                            MessageUtils.getProperty(messageSource, "role_not_found")
                    ));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ValidateException(
                                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                        MessageUtils.getProperty(messageSource, "role_not_found")
                                ));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ValidateException(
                                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                        MessageUtils.getProperty(messageSource, "role_not_found")
                                ));
                        roles.add(userRole);
                }
            });
        }

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
    public ResponseEntity<RestApiResponse> updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotExistsException("User not found with id: " + userId));

        // Create new user's account
        if (StringUtils.isNotBlank(updateUserRequest.getFullName())) {
            user.setFullName(updateUserRequest.getFullName());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getAvatar())) {
            user.setAvatar(updateUserRequest.getAvatar());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getAddress())) {
            user.setAddress(updateUserRequest.getAddress());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getUsername())) {
            if (Boolean.TRUE.equals(userRepository.existsByUsername(updateUserRequest.getUsername()))) {
                throw new ValidateException(
                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                        MessageUtils.getProperty(messageSource, "username_already_exist")
                );
            }
            user.setUsername(updateUserRequest.getUsername());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getPhone())) {
            if (Boolean.TRUE.equals(userRepository.existsByPhone(updateUserRequest.getPhone()))) {
                throw new ValidateException(
                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                        MessageUtils.getProperty(messageSource, "phone_already_exist")
                );
            }
            user.setPhone(updateUserRequest.getPhone());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getEmail())) {
            if (Boolean.TRUE.equals(userRepository.existsByEmail(updateUserRequest.getEmail()))) {
                throw new ValidateException(
                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                        MessageUtils.getProperty(messageSource, "email_already_exist")
                );
            }
            user.setEmail(updateUserRequest.getEmail());
        }
        if (StringUtils.isNotBlank(updateUserRequest.getPassword())) {
            user.setPassword(encoder.encode(updateUserRequest.getPassword()));
        }
        if (updateUserRequest.getStatus() != null) {
            user.setStatus(updateUserRequest.getStatus());
        }
        user.setModifiedBy(ERole.ROLE_ADMIN.name());

        Set<Role> roles = getRoles(updateUserRequest);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    private Set<Role> getRoles(UpdateUserRequest updateUserRequest) {
        Set<String> strRoles = updateUserRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles != null) {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ValidateException(
                                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                        MessageUtils.getProperty(messageSource, "role_not_found")
                                ));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ValidateException(
                                        ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                        MessageUtils.getProperty(messageSource, "role_not_found")
                                ));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Override
    public ResponseEntity<GetAllUserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> result = userRepository.findAll(pageable);

        List<UserInfoResponse> userList = new ArrayList<>();
        for (User u : result.getContent()) {
            userList.add(convertToUserResponse(u));
        }

        UserListResponse userListResponse = new UserListResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalPages(),
                userList
        );

        return ResponseEntity.ok(
                new GetAllUserResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        userListResponse
                )
        );
    }

    @Override
    public ResponseEntity<GetUserResponse> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotExistsException("User Not Found with user_id: " + userId));

        return ResponseEntity.ok(
                new GetUserResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        convertToUserResponse(user)
                )
        );
    }

    private UserInfoResponse convertToUserResponse(User user) {
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
