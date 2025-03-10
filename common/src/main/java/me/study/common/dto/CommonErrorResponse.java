package me.study.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.study.common.exception.type.ExceptionType;

@Getter
@NoArgsConstructor
@ToString
public class CommonErrorResponse {
	private String message;
	private String errorCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorField> errorFields;

	public CommonErrorResponse(ExceptionType exceptionType) {
		this.message = exceptionType.getMessage();
		this.errorCode = exceptionType.getErrorCode();
	}

	public CommonErrorResponse(String message, ExceptionType exceptionType) {
		this.message = message;
		this.errorCode = exceptionType.getErrorCode();
	}

	public CommonErrorResponse(ExceptionType exceptionType, List<ErrorField> errorFields) {
		this.message = exceptionType.getMessage();
		this.errorCode = exceptionType.getErrorCode();
		this.errorFields = errorFields;
	}

	public CommonErrorResponse(String errorCode, String message) {
		this.message = message;
		this.errorCode = errorCode;
	}

	@AllArgsConstructor
	@Getter
	public static class ErrorField {
		private String field;
		private String message;
	}
}