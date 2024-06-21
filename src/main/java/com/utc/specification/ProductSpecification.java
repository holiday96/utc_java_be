package com.utc.specification;

import com.utc.models.Product;
import org.springframework.data.jpa.domain.Specification;

/**
 * Project_name : UTC_Java
 *
 * @author : XuShiTa
 * @version : 1.0
 * @since : 21.6.2024
 * Description :
 */
public class ProductSpecification {

    public static Specification<Product> hasTitleLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + name.toLowerCase() + "%");
        };
    }
}
