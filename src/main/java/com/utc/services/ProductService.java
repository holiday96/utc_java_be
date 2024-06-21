package com.utc.services;

import com.utc.payload.request.AddProductRequest;
import com.utc.payload.request.UpdateProductRequest;
import com.utc.payload.response.GetAllProductResponse;
import com.utc.payload.response.GetProductResponse;
import com.utc.payload.response.RestApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
public interface ProductService {
    ResponseEntity<RestApiResponse> addProduct(AddProductRequest addProductRequest);

    ResponseEntity<RestApiResponse> updateProduct(Long productId, UpdateProductRequest updateProductRequest);

    ResponseEntity<GetAllProductResponse> getAllProduct(PageRequest pageRequest, Long minPrice, Long maxPrice);

    ResponseEntity<GetAllProductResponse> getProductListByUserId(Long userId, PageRequest pageRequest);

    ResponseEntity<GetProductResponse> getProductById(Long id);

    ResponseEntity<GetAllProductResponse> findProductByName(PageRequest pageRequest, String keyName);
}
