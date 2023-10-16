package com.passlocker.passlocker.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GenericExceptionResponse> badRequestHandler(HttpServletRequest request, BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new GenericExceptionResponse()
                                .setPath(request.getRequestURI())
                                .setMessage(badRequestException.getMessage())
                                .setStatus(HttpStatus.BAD_REQUEST.toString())
                                .setTimeStamp(new Date(System.currentTimeMillis()).toString())
                );
    }

    @ExceptionHandler
    public ResponseEntity<GenericExceptionResponse> badPermissionExceptionHandler(HttpServletRequest request, BadPermissionException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new GenericExceptionResponse()
                                .setPath(request.getRequestURI())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.FORBIDDEN.toString())
                                .setTimeStamp(new Date(System.currentTimeMillis()).toString())
                );
    }

    @ExceptionHandler
    public ResponseEntity<GenericExceptionResponse> notFoundExceptionHandler(HttpServletRequest request, NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new GenericExceptionResponse()
                                .setPath(request.getRequestURI())
                                .setMessage(exception.getMessage())
                                .setStatus(HttpStatus.NOT_FOUND.toString())
                                .setTimeStamp(new Date(System.currentTimeMillis()).toString())
                );
    }
}
