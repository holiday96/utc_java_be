package com.utc.services;

import com.utc.payload.request.AddUserRequest;
import com.utc.payload.request.UpdateUserRequest;
import com.utc.payload.response.GetAllUserResponse;
import com.utc.payload.response.GetUserResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.payload.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
public interface UserService {
    UserInfoResponse findByUsername(String username);

    ResponseEntity<RestApiResponse> addUser(AddUserRequest addUserRequest);

    ResponseEntity<RestApiResponse> updateUser(Long userId, UpdateUserRequest updateUserRequest);

    ResponseEntity<GetAllUserResponse> getAllUsers(int page, int size);

    ResponseEntity<GetUserResponse> getUserById(Long userId);
}
