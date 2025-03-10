package me.study.order.infrastructure.feign.dto.response;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;

public record ProductsResponse(
	Map<UUID, ProductResponse> products
) {
}
