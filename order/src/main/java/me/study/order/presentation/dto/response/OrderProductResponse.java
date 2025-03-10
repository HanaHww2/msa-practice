package me.study.order.presentation.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import me.study.order.model.entity.OrderItem;

public record OrderProductResponse(
	UUID orderProductId,
	UUID productId,
	String name,
	BigDecimal price,
	Integer quantity

) {
	public static OrderProductResponse from(OrderItem orderProducts) {
		return new OrderProductResponse(orderProducts.getId(),
			orderProducts.getProductId(),
			orderProducts.getName(),
			orderProducts.getPrice(),
			orderProducts.getQuantity());
	}
}
