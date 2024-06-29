package com.utc.services.impl;

import com.utc.contants.ApiStatus;
import com.utc.exception.ValidateException;
import com.utc.models.Order;
import com.utc.models.OrderDetail;
import com.utc.models.Product;
import com.utc.models.User;
import com.utc.payload.BO.CoreStatus;
import com.utc.payload.BO.ProductQuantity;
import com.utc.payload.request.OrderRequest;
import com.utc.payload.response.OrderDetailResponse;
import com.utc.payload.response.OrderResponse;
import com.utc.payload.response.PageResponse;
import com.utc.payload.response.ProductInfoResponse;
import com.utc.repository.OrderDetailRepository;
import com.utc.repository.OrderRepository;
import com.utc.repository.ProductRepository;
import com.utc.repository.UserRepository;
import com.utc.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.utc.utils.UserDetailUtil.getUserFromContext;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void create(OrderRequest orderRequest) {
        User user = getUserFromContext(userRepository);
        Long totalAmount = checkAndComputeAmountProduct(orderRequest.getProductQuantities());
        Date newDate = new Date();
        Order order = buildOrder(orderRequest, newDate, totalAmount, user.getId());
        order = orderRepository.save(order);
        List<OrderDetail> orderDetails = buildOrderDetail(order.id(), orderRequest.getProductQuantities(), newDate);
        orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<OrderResponse> getByUserIdAndStatus(Long userId, CoreStatus status) {
        List<Order> orders = orderRepository.findByUserIdAndStatus(userId, status.getCode());
        return getOrderResponses(orders);
    }

    @Override
    public List<OrderResponse> getByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return getOrderResponses(orders);
    }

    @Override
    public PageResponse<OrderResponse> gets(PageRequest pageRequest) {
        Page<Order> orders = orderRepository.findAll(pageRequest);
        List<OrderResponse> orderResponses = getOrderResponses(orders.getContent());
        return new PageResponse<OrderResponse>()
                .setPageNumber(orders.getNumber() + 1)
                .setPageSize(orders.getSize())
                .setTotalPage(orders.getTotalPages())
                .setTotalCount(orders.getTotalElements())
                .setData(orderResponses);
    }


    @Override
    public void update(Long orderId, CoreStatus status) {
        Order orderCurrent = orderRepository.findById(orderId)
                .orElseThrow(() -> new ValidateException(ApiStatus.BAD_REQUEST.toString()
                        , String.format("Order with Id: %s not found !", orderId)));
        orderCurrent.status(status.getCode());
        orderRepository.save(orderCurrent);
    }

    // Private Function

    private List<OrderResponse> getOrderResponses(List<Order> orders) {
        Map<Long, List<OrderDetail>> orderIdMapOrderDetails = orderDetailRepository.findAllByOrderIdIn(orders.stream()
                        .map(Order::id)
                        .collect(Collectors.toList()))
                .stream().collect(Collectors.groupingBy(OrderDetail::orderId));

        List<Long> productIds = orderIdMapOrderDetails.values()
                .stream()
                .flatMap(List::stream)
                .map(OrderDetail::productId)
                .collect(Collectors.toList());

        Map<Long, Product> idProduct2Product = productRepository.findAllByIdInAndStatus(productIds, 1)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return orders.stream()
                .map(order -> buildOrderResponse(order, orderIdMapOrderDetails.get(order.id()), idProduct2Product))
                .collect(Collectors.toList());
    }

    private OrderResponse buildOrderResponse(Order order, List<OrderDetail> orderDetails, Map<Long, Product> idProduct2Product) {

        return new OrderResponse()
                .setOrderId(order.id())
                .setTotalAmount(order.totalAmount())
                .setAddress(order.address())
                .setPhone(order.phone())
                .setNote(order.note())
                .setUserId(order.userId())
                .setOrderDetailResponses(
                        orderDetails.stream()
                                .map(orderDetail -> new OrderDetailResponse()
                                        .setId(orderDetail.id())
                                        .setQuantity(orderDetail.quantity())
                                        .setProductInfoResponse(new ProductInfoResponse(
                                                idProduct2Product.get(orderDetail.productId())
                                        ))
                                )
                                .collect(Collectors.toList())
                );
    }


    private List<OrderDetail> buildOrderDetail(Long orderId, List<ProductQuantity> productQuantities, Date newDate) {
        return productQuantities.stream()
                .map(productQuantity -> new OrderDetail()
                        .productId(productQuantity.getProductId())
                        .quantity(productQuantity.getQuantity())
                        .orderId(orderId)
                        .createdDate(newDate)
                        .modifiedBy("ADMIN"))
                .collect(Collectors.toList());
    }

    private Order buildOrder(OrderRequest orderRequest, Date newDate, Long totalAmount, Long userId) {
        return new Order()
                .address(orderRequest.getAddress())
                .note(orderRequest.getNote())
                .phone(orderRequest.getPhone())
                .createdDate(newDate)
                .totalAmount(totalAmount)
                .userId(userId)
                .status(CoreStatus.PENDING.getCode())
                .modifiedBy("ADMIN");
    }

    private Long checkAndComputeAmountProduct(List<ProductQuantity> productQuantities) {
        Map<Long, Integer> productIds = productQuantities.stream()
                .collect(Collectors.toMap(ProductQuantity::getProductId, ProductQuantity::getQuantity));
        List<Product> products = productRepository.findAllByIdInAndStatus(new ArrayList<>(productIds.keySet()), 1);
        if (products.size() != productQuantities.size()) {
            throw new ValidateException(ApiStatus.BAD_REQUEST.toString()
                    , "Some Product was lost the data! please try again after minute !");
        }
        products = products.stream()
                .peek(product -> product.setAmount(product.getAmount() - productIds.get(product.getId())))
                .collect(Collectors.toList());
        productRepository.saveAll(products);
        return products.stream()
                .map(Product::getPrice)
                .reduce(Long::sum)
                .orElse(0L);
    }
}
