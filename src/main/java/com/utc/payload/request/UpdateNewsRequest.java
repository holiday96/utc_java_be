package com.utc.payload.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Data
@Accessors(chain = true)
public class UpdateNewsRequest {
    @NotBlank
    String title;
    @NotBlank
    String content;
}
