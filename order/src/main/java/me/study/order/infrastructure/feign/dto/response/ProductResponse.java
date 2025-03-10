package me.study.order.infrastructure.feign.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ProductResponse(
	UUID id,
	String name,
	BigDecimal price,
	Integer stock
) {
}
