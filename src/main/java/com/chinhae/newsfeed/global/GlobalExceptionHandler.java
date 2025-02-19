package com.chinhae.newsfeed.global;

import com.chinhae.newsfeed.global.dto.error.ErrorCode;
import com.chinhae.newsfeed.global.dto.error.ErrorResponse;
import com.chinhae.newsfeed.global.dto.error.detail.AbstractErrorDetail;
import com.chinhae.newsfeed.global.dto.error.detail.FieldErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<AbstractErrorDetail> IllegalArgumentException(IllegalArgumentException e) {
        return ErrorResponse.of(ErrorCode.NO_SUCH_ELEMENT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<FieldErrorDetail> handleValidationException(MethodArgumentNotValidException e) {
        return ErrorResponse.of(ErrorCode.VALIDATION, e.getBindingResult());
    }
}
