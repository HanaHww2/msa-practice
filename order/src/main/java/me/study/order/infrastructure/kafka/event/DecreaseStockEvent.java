package me.study.order.infrastructure.kafka.event;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import me.study.common.dto.DecreaseProductStockRequest;

@Builder
public record DecreaseStockEvent(
	UUID orderId,
	List<DecreaseProductStockRequest> decreaseProductStockRequests
) {

	public static DecreaseStockEvent of(UUID orderId, List<DecreaseProductStockRequest> decreaseRequests) {
		return DecreaseStockEvent.builder()
			.orderId(orderId)
			.decreaseProductStockRequests(decreaseRequests)
			.build();
	}
}
