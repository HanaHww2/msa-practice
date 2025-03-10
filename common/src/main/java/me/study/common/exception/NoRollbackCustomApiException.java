package me.study.common.exception;

import me.study.common.exception.type.ExceptionType;

public class NoRollbackCustomApiException extends CustomApiException {

    public NoRollbackCustomApiException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
