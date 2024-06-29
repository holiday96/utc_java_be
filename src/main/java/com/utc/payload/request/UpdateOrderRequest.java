package com.utc.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    String note;
    @NotBlank
    @Size(max = 11, min = 10)
    String phone;
    @NotBlank
    String address;
}
