package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
