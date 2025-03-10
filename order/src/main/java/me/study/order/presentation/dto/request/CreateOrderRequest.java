package me.study.order.presentation.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import me.study.order.model.entity.Order;
import me.study.order.model.enums.OrderStatus;

public record CreateOrderRequest(
	@NotEmpty
	@Valid
	List<CreateOrderProductRequest> orderProducts
) {
	public Order toEntity() {
		return Order.of(OrderStatus.ACCEPTED);
	}
}
