package com.utc.services;

import com.utc.payload.request.OrderRequest;
import com.utc.payload.response.OrderResponse;
import com.utc.payload.response.PageResponse;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    void create(OrderRequest orderRequest);
    PageResponse<OrderResponse> getByUserIdAndStatus(Long userId, Integer status, PageRequest pageRequest);

    PageResponse<OrderResponse> getByUserId(Long userId, PageRequest pageRequest);

    PageResponse<OrderResponse> gets(PageRequest pageRequest);

    void update(Long orderId, Integer status);
}
