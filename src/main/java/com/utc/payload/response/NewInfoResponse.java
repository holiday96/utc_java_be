package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

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
public class NewInfoResponse {
    Long id;

    String title;

    String content;

    Integer status;
}
