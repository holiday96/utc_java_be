package com.utc.payload.response;

import com.utc.models.Category;
import com.utc.models.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_name : UTC_Java
 *
 * @author : datmt
 * @version : 1.0
 * @since : 6.6.2024
 * Description :
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Accessors(chain = true)
public class ProductInfoResponse {
    Long id;

    String title;

    String content;

    String image;

    Long price;

    Long amount;

    String unit;

    Integer status;

    List<Long> categories;

    Long userId;

    public ProductInfoResponse(Product product) {
        this.id = product.getId();

        this.title = product.getTitle();

        this.content = product.getContent();

        this.image = product.getImage();

        this.price = product.getPrice();

        this.amount = product.getAmount();

        this.unit = product.getUnit();

        this.status = product.getStatus();

        this.categories = product.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        this.userId = product.getUserId();
    }
}
