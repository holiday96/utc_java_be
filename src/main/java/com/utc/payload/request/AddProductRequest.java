package com.utc.payload.request;

import lombok.Data;

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
public class AddProductRequest {
    @NotBlank
    @Size(max = 128)
    private String title;

    @Min(1)
    @Max(999999999)
    private Long price;

    @Min(1)
    @Max(999999999)
    private Long amount;

    @NotBlank
    @Size(max = 10)
    private String unit;

    private Set<Long> categories;
}
