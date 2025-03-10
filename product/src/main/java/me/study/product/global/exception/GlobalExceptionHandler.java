package me.study.product.global.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import me.study.common.dto.CommonErrorResponse;
import me.study.common.exception.CustomApiException;
import me.study.common.exception.type.BaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 공통 커스텀 예외를 기준으로 오류를 제어한다.
	@ExceptionHandler(value = {CustomApiException.class})
	protected ResponseEntity<Object> handleCustomApiException(
		CustomApiException e) {

		return ResponseEntity.status(e.getStatus())
			.body(new CommonErrorResponse(e.getErrorCode(), e.getMessage()));
	}

	@ExceptionHandler(value = {ValidationException.class})
	protected ResponseEntity<Object> handleValidationException(
		ValidationException e) {

		return ResponseEntity.status(BaseException.INVALID_INPUT.getStatus())
			.body(new CommonErrorResponse(e.getMessage(), BaseException.INVALID_INPUT));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException e) {

		return ResponseEntity.status(BaseException.SERVER_ERROR.getStatus())
			.body(new CommonErrorResponse(e.getMessage(), BaseException.SERVER_ERROR));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {

		List<CommonErrorResponse.ErrorField> errorFields = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(err -> new CommonErrorResponse.ErrorField(err.getField(), err.getDefaultMessage()))
			.toList();

		return ResponseEntity.status(BaseException.INVALID_INPUT.getStatus())
			.body(new CommonErrorResponse(BaseException.INVALID_INPUT, errorFields));
	}

	//
	// @ExceptionHandler(DataIntegrityViolationException.class)
	// protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
	//
	// 	return ResponseEntity.status(BaseException.DUPLICATE_FIELD.getStatus())
	// 		.body(new CommonErrorResponse(BaseException.DUPLICATE_FIELD));
	// }

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> internalServerError(Exception ex) {

		return ResponseEntity.status(BaseException.SERVER_ERROR.getStatus())
			.body(new CommonErrorResponse(ex.getMessage(), BaseException.SERVER_ERROR));
	}


}
