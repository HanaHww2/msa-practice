package me.study.product.presentation.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import me.study.product.model.entity.Product;

@Builder
public record ProductResponse(
	UUID id,
	String name,
	BigDecimal price,
	Integer stock
) {
	public static ProductResponse from(Product product) {
		return ProductResponse.builder()
			.id(product.getId())
			.name(product.getName())
			.price(product.getPrice().getPrice())
			.stock(product.getStock().getStockQuantity())
			.build();
	}
}
