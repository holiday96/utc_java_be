package com.utc.payload.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long orderId;
    Integer totalAmount;
    String address;
    String phone;
    String note;
    Long userId;
    List<OrderDetailResponse> orderDetailResponses;
}
