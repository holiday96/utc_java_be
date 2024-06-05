package com.utc.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
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
public class UpdateUserRequest {
    @Size(max = 64)
    private String fullName;

    @Size(max = 255)
    private String address;

    @Size(max = 15)
    @Pattern(
            regexp = "^\\d{1,15}$",
            message = "{phone_invalid}"
    )
    private String phone;

    @Size(max = 50)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "{email_invalid}"
    )
    private String email;

    private String avatar;

    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 6, max = 40)
    private String password;

    private Set<String> role;

    @Min(-1)
    @Max(1)
    private Integer status;
}
