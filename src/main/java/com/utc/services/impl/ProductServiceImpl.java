package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.EStatus;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.models.Category;
import com.utc.models.Product;
import com.utc.models.User;
import com.utc.payload.request.AddProductRequest;
import com.utc.payload.request.UpdateProductRequest;
import com.utc.payload.response.GetAllProductResponse;
import com.utc.payload.response.ProductInfoResponse;
import com.utc.payload.response.ProductListResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.repository.CategoryRepository;
import com.utc.repository.ProductRepository;
import com.utc.repository.UserRepository;
import com.utc.services.ProductService;
import com.utc.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 29.5.2024
 * Description :
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<RestApiResponse> addProduct(AddProductRequest addProductRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Product product = Product.builder()
                .userId(user.getId())
                .title(addProductRequest.getTitle())
                .price(addProductRequest.getPrice())
                .amount(addProductRequest.getAmount())
                .unit(addProductRequest.getUnit())
                .status(EStatus.ACTIVE.code)
                .modifiedBy(username)
                .build();

        Set<Long> strCategories = addProductRequest.getCategories();
        Set<Category> categories = new HashSet<>();

        if (strCategories != null) {
            strCategories.forEach(categoryId -> {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ValidateException(
                                ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                MessageUtils.getProperty(messageSource, "category_not_found")
                        ));
                categories.add(category);
            });
            product.setCategories(categories);
        }
        productRepository.save(product);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product productCurrent = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotExistsException(
                        String.format(MessageUtils.getProperty(messageSource, "product_not_found"), productId)
                ));

        if (StringUtils.isNotBlank(updateProductRequest.getTitle())) {
            productCurrent.setTitle(updateProductRequest.getTitle());
        }
        if (updateProductRequest.getPrice() != null) {
            productCurrent.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getAmount() != null) {
            productCurrent.setAmount(updateProductRequest.getAmount());
        }
        if (StringUtils.isNotBlank(updateProductRequest.getUnit())) {
            productCurrent.setUnit(updateProductRequest.getUnit());
        }
        if (updateProductRequest.getStatus() != null) {
            productCurrent.setStatus(updateProductRequest.getStatus());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        productCurrent.setModifiedBy(username);

        Set<Long> strCategories = updateProductRequest.getCategories();
        if (strCategories != null) {
            Set<Category> categories = new HashSet<>();

            strCategories.forEach(categoryId -> {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ValidateException(
                                ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                String.format(MessageUtils.getProperty(messageSource, "category_not_found"), categoryId)
                        ));
                categories.add(category);
            });
            productCurrent.setCategories(categories);
        }
        productRepository.save(productCurrent);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<GetAllProductResponse> getProductListByUserId(Long userId, PageRequest pageRequest) {
        Page<Product> result = productRepository.findAllByUserId(userId, pageRequest);

        List<ProductInfoResponse> productList = result.getContent()
                .stream()
                .map(this::convertToInfoResponse)
                .collect(Collectors.toList());

        ProductListResponse productListResponse = new ProductListResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalPages(),
                productList
        );
        return ResponseEntity.ok(
                new GetAllProductResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        productListResponse
                )
        );
    }

    private ProductInfoResponse convertToInfoResponse(Product product) {
        List<Long> categories = product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        return new ProductInfoResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getAmount(),
                product.getUnit(),
                product.getStatus(),
                categories
        );
    }
}
