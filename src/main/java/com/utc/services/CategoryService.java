package com.utc.services;

import com.utc.payload.request.CreateCategoryRequest;
import com.utc.payload.request.UpdateCategoryRequest;
import com.utc.payload.response.GetAllCategoryResponse;
import com.utc.payload.response.GetCategoryResponse;
import com.utc.payload.response.RestApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 8.6.2024
 * Description :
 */
public interface CategoryService {
    ResponseEntity<GetAllCategoryResponse> getCategoryListByUserId(Long userId, Pageable pageable);

    ResponseEntity<GetCategoryResponse> getCategory(Long categoryId);

    ResponseEntity<RestApiResponse> create(CreateCategoryRequest createCategoryRequest);

    ResponseEntity<RestApiResponse> update(Long id, UpdateCategoryRequest updateCategoryRequest);
}
