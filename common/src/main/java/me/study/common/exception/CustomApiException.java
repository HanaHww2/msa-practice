package me.study.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import me.study.common.exception.type.BaseException;
import me.study.common.exception.type.ExceptionType;

@Getter
public class CustomApiException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;
    private ExceptionType exceptionType;

    public CustomApiException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType= exceptionType;
        this.errorCode = exceptionType.getErrorCode();
        this.status= exceptionType.getStatus();
    }

    public CustomApiException(String message) {
        super(message);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.errorCode = BaseException.SERVER_ERROR.getErrorCode();
        this.status= HttpStatus.BAD_REQUEST;
    }

    public CustomApiException(String message, HttpStatus status) {
        super(message);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.errorCode = BaseException.SERVER_ERROR.getErrorCode();
        this.status= status;
    }

    public CustomApiException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.errorCode = errorCode;
        this.status= status;
    }

    public CustomApiException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.errorCode = BaseException.SERVER_ERROR.getErrorCode();
        this.status = exceptionType.getStatus();
    }

    public CustomApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.exceptionType = BaseException.SERVER_ERROR;
        this.errorCode = BaseException.SERVER_ERROR.getErrorCode();
        this.status = status;
    }
}
