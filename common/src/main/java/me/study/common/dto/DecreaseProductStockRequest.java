package me.study.common.dto;

import java.util.UUID;

public record DecreaseProductStockRequest(
	UUID productId,
	Integer quantity
) {
}
