package com.utc.controllers;


import com.utc.payload.request.CreateCategoryRequest;
import com.utc.payload.request.UpdateCategoryRequest;
import com.utc.payload.response.GetAllCategoryResponse;
import com.utc.payload.response.GetCategoryResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 8.6.2024
 * Description :
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<RestApiResponse> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        log.info("Request createCategory: {}", createCategoryRequest);
        return categoryService.create(createCategoryRequest);
    }

    @PatchMapping("/{category_id}")
    public ResponseEntity<RestApiResponse> updateCategory(
            @PathVariable("category_id") Long id,
            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) {
        log.info("Request updateCategory: {}", updateCategoryRequest);
        return categoryService.update(id, updateCategoryRequest);
    }

    @GetMapping("/info/{user_id}")
    public ResponseEntity<GetAllCategoryResponse> getCategoryListByUserId(
            @PathVariable("user_id") Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "100") @Min(1) @Max(100) int size
    ) {
        log.info("Request getCategoryList: userId={}, page={}, size={}", userId, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        return categoryService.getCategoryListByUserId(userId, pageable);
    }

    @GetMapping("/info/cate/{category_id}")
    public ResponseEntity<GetCategoryResponse> getCategory(
            @PathVariable("category_id") Long categoryId
    ) {
        log.info("Request getCategory: categoryId={}", categoryId);
        return categoryService.getCategory(categoryId);
    }
}
