package com.utc.payload.request;

import com.utc.payload.BO.ProductQuantity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String note;
    @NotBlank
    @Size(max = 11, min = 10)
    String phone;
    @NotBlank
    String address;
    @NotEmpty
    List<ProductQuantity> productQuantities;
}
