package com.utc.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductRequest {
    @NotBlank
    @Size(max = 128)
    String title;

    @NotBlank
    @Size(max = 10000)
    String content;

    @Min(1)
    @Max(999999999)
    Long price;

    @Min(1)
    @Max(999999999)
    Long amount;

    @NotBlank
    @Size(max = 10)
    String unit;

    Set<Long> categories;
}
