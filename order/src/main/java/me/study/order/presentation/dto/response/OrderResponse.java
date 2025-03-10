package me.study.order.presentation.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import me.study.order.model.entity.Order;
import me.study.order.model.enums.OrderStatus;

public record OrderResponse(
	UUID orderId,
	List<OrderProductResponse> orderProducts,
	BigDecimal orderTotalPrice,
	OrderStatus orderStatus
) {
	public static OrderResponse from(Order order) {
		return new OrderResponse(order.getId(),
			order.getOrderItems().stream().map(OrderProductResponse::from).toList(),
			order.getTotalPrice(),
			order.getOrderStatus());
	}
}
