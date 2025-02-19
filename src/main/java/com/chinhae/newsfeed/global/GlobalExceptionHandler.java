package com.chinhae.newsfeed.global;

import com.chinhae.newsfeed.domain.profile.exception.IllegalFriendWorkException;
import com.chinhae.newsfeed.global.dto.error.ErrorCode;
import com.chinhae.newsfeed.global.dto.error.ErrorResponse;
import com.chinhae.newsfeed.global.dto.error.detail.AbstractErrorDetail;
import com.chinhae.newsfeed.global.dto.error.detail.FieldErrorDetail;
import com.chinhae.newsfeed.web.interceptor.exception.UnauthorizedException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<FieldErrorDetail> handleValidationExceptionHandler(
        MethodArgumentNotValidException e) {
        log.info("handleValidationExceptionHandler", e);
        return ErrorResponse.of(ErrorCode.VALIDATION, e.getBindingResult());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<AbstractErrorDetail> handleIllegalArgumentException(
        IllegalArgumentException e) {
        log.info("handleIllegalArgumentException", e);
        return ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<AbstractErrorDetail> handleNoSuchElementException(
        NoSuchElementException e) {
        log.info("handleNoSuchElementException", e);
        return ErrorResponse.of(ErrorCode.NO_SUCH_ELEMENT, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse<AbstractErrorDetail> handleUnauthorizedException(
        UnauthorizedException e) {
        log.info("handleUnauthorizedException", e);
        return ErrorResponse.of(ErrorCode.AUTHORIZAION, e.getMessage());
    }

    @ExceptionHandler(IllegalFriendWorkException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<AbstractErrorDetail> handleIllegalFriendWorkException(
        IllegalFriendWorkException e
    ) {
        log.info("handleIllegalFriendWorkException", e);
        return ErrorResponse.of(ErrorCode.ILLEGAL_FRIEND_TASK, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<AbstractErrorDetail> handleException(Exception e) {
        log.info("handleException", e);
        return ErrorResponse.of(ErrorCode.EXCEPTION, e.getMessage());
    }

}
