package com.utc.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 07.06.2024
 * Description :
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductRequest {

    @Size(max = 128)
    String title;

    @Min(1)
    @Max(999999999)
    Long price;

    @Min(1)
    @Max(999999999)
    Long amount;

    @Size(max = 10)
    String unit;

    Set<Long> categories;

    @Min(-1)
    @Max(1)
    Integer status;
}
