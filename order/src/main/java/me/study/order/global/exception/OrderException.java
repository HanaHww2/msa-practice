package me.study.order.global.exception;

import org.springframework.http.HttpStatus;

import me.study.common.exception.type.ExceptionType;

public enum OrderException implements ExceptionType {

	NOT_ENOUGH_TOTAL_PRICE(HttpStatus.UNPROCESSABLE_ENTITY, "총계 금액이 최소 30.000원 이상이어야 합니다.", "E_NOT_ENOUGH_TOTAL"),
	NOT_ENOUGH_STOCK(HttpStatus.CONFLICT, "재고가 주문 수량보다 적습니다.", "E_NOT_ENOUGH_STOCK"),
	INVALID_PRODUCT_INFO(HttpStatus.BAD_REQUEST, "주문 정보가 올바르지 않습니다.", "E_INVALID_PRODUCT_INFO"),
	INVALID_ORDER_ID(HttpStatus.NOT_FOUND, "주문 아이디가 존재하지 않습니다.", "E_INVALID_ORDER_ID");

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	OrderException(HttpStatus status, String message, String errorCode) {
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
	}

	@Override
	public HttpStatus getStatus() {
		return this.status;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}
}
