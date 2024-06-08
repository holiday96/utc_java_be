package com.utc.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCategoryRequest {

    @Size(max = 64)
    String name;

    @Min(-1)
    @Max(1)
    Integer status;
}
