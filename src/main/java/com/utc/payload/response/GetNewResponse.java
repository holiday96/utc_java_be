package com.utc.payload.response;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GetNewResponse {
    private Integer status;
    private String message;
    private NewInfoResponse result;
}
