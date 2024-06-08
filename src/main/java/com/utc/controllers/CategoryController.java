package com.utc.controllers;


import com.utc.payload.request.CreateCategoryRequest;
import com.utc.payload.request.UpdateCategoryRequest;
import com.utc.payload.response.GetAllCategoryResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/info")
    public ResponseEntity<GetAllCategoryResponse> getCategoryList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "100") @Min(1) @Max(100) int size
    ) {
        log.info("Request getCategoryList: page={}, size={}", page, size);
        return categoryService.getCategoryList(PageRequest.of(page - 1, size));
    }
}
