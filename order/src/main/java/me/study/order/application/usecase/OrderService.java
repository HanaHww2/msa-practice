package me.study.order.application.usecase;

import java.util.UUID;

import me.study.order.presentation.dto.request.CreateOrderRequest;
import me.study.order.presentation.dto.response.OrderResponse;

public interface OrderService {

	OrderResponse createOrderWithProducts(CreateOrderRequest request);

	boolean cancelOrder(UUID orderId);
}
