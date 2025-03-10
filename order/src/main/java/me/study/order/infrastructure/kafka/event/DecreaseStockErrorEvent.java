package me.study.order.infrastructure.kafka.event;

import java.util.UUID;

public record DecreaseStockErrorEvent(
	UUID orderId
) {
}
