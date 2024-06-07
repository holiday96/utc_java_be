package com.utc.payload.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
//@Accessors(chain = true, fluent = true)
public class UpdateNewRequest {

    @Size(max = 128)
    String title;

    @Size(max = 10000)
    String content;

    @Min(-1)
    @Max(1)
    Integer status;
}
