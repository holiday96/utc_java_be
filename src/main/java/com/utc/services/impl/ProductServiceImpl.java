package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.EStatus;
import com.utc.exception.ValidateException;
import com.utc.models.Category;
import com.utc.models.Product;
import com.utc.models.User;
import com.utc.payload.request.AddProductRequest;
import com.utc.payload.response.RestApiResponse;
import com.utc.repository.CategoryRepository;
import com.utc.repository.ProductRepository;
import com.utc.repository.UserRepository;
import com.utc.services.ProductService;
import com.utc.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
}
