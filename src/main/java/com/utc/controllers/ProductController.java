package com.utc.controllers;

import com.utc.payload.request.AddProductRequest;
import com.utc.payload.request.UpdateProductRequest;
import com.utc.payload.response.GetAllProductResponse;
import com.utc.payload.response.GetProductResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 27.5.2024
 * Description :
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<RestApiResponse> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        log.info("Request addProduct: {}", addProductRequest);
        return productService.addProduct(addProductRequest);
    }

    @PatchMapping("/{product_id}")
    public ResponseEntity<RestApiResponse> updateProduct(
            @PathVariable("product_id") Long productId,
            @Valid @RequestBody UpdateProductRequest updateProductRequest
    ) {
        log.info("Request updateProduct: {}", updateProductRequest);
        return productService.updateProduct(productId, updateProductRequest);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<GetAllProductResponse> getProductListByUserId(
            @PathVariable("user_id") Long userId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        log.info("Request getProductListByUserId: page={}, size={}", page, size);
        return productService.getProductListByUserId(userId, PageRequest.of(page - 1, size));
    }

    @GetMapping("/info/{product_id}")
    public ResponseEntity<GetProductResponse> getProductById(@PathVariable("product_id") @Positive Long id) {
        log.info("Request getProductById: {}", id);
        return productService.getProductById(id);
    }
}
