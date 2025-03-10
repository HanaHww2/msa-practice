package me.study.product.infrastructure.kafka.event;

import java.util.UUID;

public record DecreaseStockErrorEvent(
	UUID orderId
) {
}
