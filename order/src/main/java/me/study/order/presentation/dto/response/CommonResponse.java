package me.study.order.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	public CommonResponse(String message, T result) {
		this.message = message;
		this.result = result;
	}
}