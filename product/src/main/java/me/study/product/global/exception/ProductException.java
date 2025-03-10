package me.study.product.global.exception;

import org.springframework.http.HttpStatus;

import me.study.common.exception.type.ExceptionType;

public enum ProductException implements ExceptionType {

	INVALID_PRD_ID(HttpStatus.NOT_FOUND, "상품 아이디에 부합하는 상품이 존재하지 않습니다.", "E_INVALID_PRD_ID"),
	UNAVAILABLE_STOCK(HttpStatus.CONFLICT, "상품 재고가 부족합니다.", "E_UNAVAILABLE_STOCK"),
	INVALID_STOCK(HttpStatus.BAD_REQUEST, "상품 재고는 최소 1개 이상이어야 합니다.", "E_INVALID_STOCK"),;

	private final HttpStatus status;
	private final String message;
	private final String errorCode;

	ProductException(HttpStatus status, String message, String errorCode) {
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
