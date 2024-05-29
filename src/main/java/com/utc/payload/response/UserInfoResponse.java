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
    private String fullName;
    private String avatar;
    private String address;
    private String phone;
    private String email;
    private String username;
    private Integer status;
    private List<String> roles;
}
