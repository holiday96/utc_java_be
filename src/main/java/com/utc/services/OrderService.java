package com.utc.services;

import com.utc.payload.request.OrderRequest;
import com.utc.payload.response.OrderResponse;
import com.utc.payload.response.PageResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface OrderService {
    void create(OrderRequest orderRequest);
    List<OrderResponse> getByUserIdAndStatus(Long userId, Integer status);

    List<OrderResponse> getByUserId(Long userId);

    PageResponse<OrderResponse> gets(PageRequest pageRequest);

    void update(Long orderId, Integer status);
}
