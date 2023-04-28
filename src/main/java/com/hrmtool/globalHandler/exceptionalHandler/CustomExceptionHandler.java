package com.hrmtool.globalHandler.exceptionalHandler;

import com.hrmtool.globalHandler.BadRequestException;
import com.hrmtool.globalHandler.NotFoundException;
import com.hrmtool.globalHandler.UnauthorizedException;
import com.hrmtool.globalHandler.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception ex, WebRequest request) {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<ApiResponse<?>> handleUnauthorizedException(Exception ex, WebRequest request) {
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
