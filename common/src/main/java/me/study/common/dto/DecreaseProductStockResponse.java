package me.study.common.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record DecreaseProductStockResponse(
	UUID productId,
	DecreaseStatus decreaseStatus
) {

	public static DecreaseProductStockResponse of(UUID productId, boolean result) {

		return DecreaseProductStockResponse.builder()
			.productId(productId)
			.decreaseStatus(result
				? DecreaseProductStockResponse.DecreaseStatus.SUCCESS
				: DecreaseProductStockResponse.DecreaseStatus.FAIL)
			.build();
	}

	enum DecreaseStatus {
		SUCCESS,
		FAIL
	}
}
