package com.utc.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 8.6.2024
 * Description :
 */
@Data
public class CreateCategoryRequest {

    @NotBlank
    @Size(max = 64)
    private String name;
}
