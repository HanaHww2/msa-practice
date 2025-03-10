package me.study.order.presentation.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.study.order.infrastructure.feign.dto.response.ProductResponse;
import me.study.order.model.entity.OrderItem;

public record CreateOrderProductRequest(
	@NotNull
	UUID productId,
	@NotBlank
	String productName,
	@DecimalMin("1000.00")
	@Digits(integer = 10, fraction = 2)
	BigDecimal productPrice,
	@Min(0)
	Integer productQuantity
) {
	public OrderItem toEntity() {
		return OrderItem.builder()
			.productId(this.productId)
			.name(this.productName)
			.price(this.productPrice)
			.quantity(this.productQuantity)
			.build();
	}
}
