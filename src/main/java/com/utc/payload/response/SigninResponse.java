package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninResponse {
    private Integer status;
    private String message;
    private UserInfoResponse result;
    private String token;
}
