package com.utc.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    @NotNull
    private Long roleId;

    @NotNull
    @Size(max = 64)
    private String fullName;

    private String avatar;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    private String phone;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;
}
