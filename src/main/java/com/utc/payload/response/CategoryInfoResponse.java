package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 8.6.2024
 * Description :
 */
@Data
@AllArgsConstructor
public class CategoryInfoResponse {
    Long id;

    String name;

    Integer status;
}
