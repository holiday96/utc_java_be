package com.utc.controllers;

import com.utc.payload.request.AddProductRequest;
import com.utc.payload.request.AddUserRequest;
import com.utc.payload.request.UpdateUserRequest;
import com.utc.payload.response.GetAllUserResponse;
import com.utc.payload.response.GetUserResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
