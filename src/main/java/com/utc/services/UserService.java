package com.utc.services;

import com.utc.payload.response.UserInfoResponse;

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
}
