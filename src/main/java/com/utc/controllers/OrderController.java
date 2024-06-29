package com.utc.controllers;

import com.utc.contants.ApiStatus;
import com.utc.payload.BO.CoreStatus;
import com.utc.payload.request.OrderRequest;
import com.utc.payload.response.OrderResponse;
import com.utc.payload.response.PageResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<RestApiResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.create(orderRequest);
        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponse>> getByUserId(@PathVariable Long id,
                                                           @RequestParam(required = false) String status) {
        List<OrderResponse> orderResponses;
        if (status == null) {
            orderResponses = orderService.getByUserId(id);
        } else {
            orderResponses = orderService.getByUserIdAndStatus(id, CoreStatus.valueOf(status));
        }
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> gets(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(orderService.gets(PageRequest.of(page - 1, size)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestApiResponse> update(@PathVariable Long id,
                                    @RequestParam String status) {
        orderService.update(id, CoreStatus.valueOf(status));
        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

}
