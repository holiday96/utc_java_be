package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 6.5.2024
 * Description :
 */
@Data
@AllArgsConstructor
public class GetUserResponse {
    private Integer status;
    private String message;
    private UserInfoResponse result;
}
