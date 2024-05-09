package com.microserviceshop.OrderService.service;

import com.microserviceshop.OrderService.model.OrderRequest;
import com.microserviceshop.OrderService.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
