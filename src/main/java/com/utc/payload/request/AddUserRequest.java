package com.utc.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@Data
public class AddUserRequest {
    @NotBlank
    @Size(max = 64)
    private String fullName;

    @NotBlank
    @Size(max = 255)
    private String address;

    @NotBlank
    @Size(max = 15)
    @Pattern(
            regexp = "^\\d{1,15}$",
            message = "{phone_invalid}"
    )
    private String phone;

    @NotBlank
    @Size(max = 50)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "{email_invalid}"
    )
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;
}
