package com.utc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 7.6.2024
 * Description :
 */
@Data
@AllArgsConstructor
public class ProductListResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private List<ProductInfoResponse> productList;
}
