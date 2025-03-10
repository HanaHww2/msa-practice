package me.study.product.presentation.dto.response;

import java.util.Map;
import java.util.UUID;


public record ProductsResponse(
	Map<UUID, ProductResponse> products
) {
}
