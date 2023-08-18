package com.ys.rental.adapter.in;

import com.ys.infrastructure.exception.BadRequestException;
import com.ys.infrastructure.exception.UnauthorizedException;
import com.ys.infrastructure.utils.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity handleServletException(ServletException ex) {
        log.error("ServletException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity handleHttpMessageConversionException(HttpMessageConversionException ex) {
        log.error("HttpMessageConversionException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException ex) {
        log.error("BindException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedException ex) {
        log.error("UnauthorizedException", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException ex) {
        log.error("NoSuchElementException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleIllegalStateException(IllegalStateException ex) {
        log.error("IllegalStateException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
