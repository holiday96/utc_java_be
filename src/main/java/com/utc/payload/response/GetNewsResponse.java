package com.utc.payload.response;

import lombok.Data;
import lombok.experimental.Accessors;

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
public class GetNewsResponse {
    private Integer status;
    private String message;
    private NewsInfoResponse result;
}
