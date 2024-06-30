package com.utc.controllers;

import com.utc.contants.ApiStatus;
import com.utc.payload.request.OrderRequest;
import com.utc.payload.response.OrderResponse;
import com.utc.payload.response.PageResponse;
import com.utc.payload.response.RestApiResponse;
import com.utc.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

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
    public ResponseEntity<PageResponse<OrderResponse>> getByUserId(@RequestParam(defaultValue = "1", required = false) @Min(1) int page,
                                                           @RequestParam(defaultValue = "10", required = false) @Min(1) @Max(100) int size,
                                                           @PathVariable Long id,
                                                           @RequestParam(required = false) Integer status) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdDate");
        PageResponse<OrderResponse> orderResponses;
        if (status == null) {
            orderResponses = orderService.getByUserId(id, PageRequest.of(page - 1, size, sort));
        } else {
            orderResponses = orderService.getByUserIdAndStatus(id, status, PageRequest.of(page - 1, size, sort));
        }
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> gets(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdDate");
        return ResponseEntity.ok(orderService.gets(PageRequest.of(page - 1, size, sort)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestApiResponse> update(@PathVariable Long id,
                                                  @RequestParam Integer status) {
        log.info("Request updateOrder: id={}, status={}", id, status);
        orderService.update(id, status);
        return ResponseEntity.ok(
                new RestApiResponse(
                        ApiStatus.SUCCESS.code,
                        ApiStatus.SUCCESS.toString().toLowerCase()
                )
        );
    }

}
