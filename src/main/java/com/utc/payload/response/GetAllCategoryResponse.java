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
public class GetAllCategoryResponse {
    private Integer status;
    private String message;
    private CategoryListResponse result;
}
