package com.utc.payload.BO;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Accessors(chain = true)
public class ProductQuantity {
    @NotNull
            @Positive
    Long productId;
    @NotNull
            @Positive
    Integer quantity;
}
