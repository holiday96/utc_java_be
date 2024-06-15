package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.contants.EStatus;
import com.utc.exception.ResourceNotExistsException;
import com.utc.exception.ValidateException;
import com.utc.models.Category;
import com.utc.models.User;
import com.utc.payload.request.CreateCategoryRequest;
import com.utc.payload.request.UpdateCategoryRequest;
import com.utc.payload.response.*;
import com.utc.repository.CategoryRepository;
import com.utc.repository.UserRepository;
import com.utc.services.CategoryService;
import com.utc.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 8.6.2024
 * Description :
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final MessageSource messageSource;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<GetAllCategoryResponse> getCategoryListByUserId(Long userId, Pageable pageable) {
        Page<Category> result = categoryRepository.findByUserId(userId, pageable);

        List<CategoryInfoResponse> categoryList = result.getContent()
                .stream()
                .map(category -> new CategoryInfoResponse(
                        category.getId(),
                        category.getName(),
                        category.getStatus())
                )
                .collect(Collectors.toList());

        CategoryListResponse categoryListResponse = new CategoryListResponse(
                result.getNumber() + 1,
                result.getSize(),
                result.getTotalPages(),
                categoryList
        );
        return ResponseEntity.ok(
                new GetAllCategoryResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        categoryListResponse
                )
        );
    }

    @Override
    public ResponseEntity<GetCategoryResponse> getCategory(Long categoryId) {
        CategoryInfoResponse result = categoryRepository.findById(categoryId)
                .map(item -> new CategoryInfoResponse(
                        item.getId(),
                        item.getName(),
                        item.getStatus())
                )
                .orElseThrow(() -> new ResourceNotExistsException(
                        String.format(MessageUtils.getProperty(messageSource, "category_not_found"), categoryId)
                ));

        return ResponseEntity.ok(
                new GetCategoryResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase(),
                        result
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> create(CreateCategoryRequest createCategoryRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        categoryRepository.findByUserIdAndName(user.getId(), createCategoryRequest.getName())
                .ifPresent(cate -> {
                    throw new ValidateException(
                            ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                            MessageUtils.getProperty(messageSource, "category_already_exist")
                    );
                });

        Category category = Category.builder()
                .userId(user.getId())
                .name(createCategoryRequest.getName())
                .status(EStatus.ACTIVE.code)
                .modifiedBy(username)
                .build();

        categoryRepository.save(category);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @Override
    public ResponseEntity<RestApiResponse> update(Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
        Category cateCurrent = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotExistsException(
                        String.format(MessageUtils.getProperty(messageSource, "category_not_found"), categoryId)
                ));

        if (StringUtils.isNotBlank(updateCategoryRequest.getName())) {
            categoryRepository.findByUserIdAndName(cateCurrent.getUserId(), updateCategoryRequest.getName())
                    .ifPresent(cate -> {
                        throw new ValidateException(
                                ApiStatus.BAD_REQUEST.toString().toLowerCase(),
                                MessageUtils.getProperty(messageSource, "category_already_exist")
                        );
                    });

            cateCurrent.setName(updateCategoryRequest.getName());
        }
        if (updateCategoryRequest.getStatus() != null) {
            cateCurrent.setStatus(updateCategoryRequest.getStatus());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        cateCurrent.setModifiedBy(username);

        categoryRepository.save(cateCurrent);

        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }
}
