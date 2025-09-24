package com.josephadogeridev.factory.exceptions;

import com.github.dockerjava.api.exception.BadRequestException;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.josephadogeridev.factory.utils.RequestContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(ResourceNotFoundException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();

        errorDetails.put("message", "Resource not found: " + ex.getMessage());
        errorDetails.put("code", "RESOURCE_NOT_FOUND");
        errorDetails.put("url", request.getRequestURI());
        errorDetails.put("method", request.getMethod());
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Invalid argument: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class) // Handles a specific custom exception
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "NOT_FOUND");
        errorDetails.put("url", request.getRequestURI());
        errorDetails.put("method", request.getMethod());
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class) // Handles conflict exception
    public ResponseEntity<Map<String, String>> handleConflictException(ResourceConflictException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "CONFLICT");
        errorDetails.put("url", request.getRequestURI());
        errorDetails.put("method", request.getMethod());
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class) // Handles bad request exception
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("code", "BAD_REQUEST");
        errorDetails.put("url", request.getRequestURI());
        errorDetails.put("method", request.getMethod());
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) // Handles all other generic exceptions
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        HttpServletRequest request = RequestContextUtil.getCurrentHttpRequest();

        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", "An unexpected error occurred.");
        errorDetails.put("code", "INTERNAL_SERVER_ERROR");
        errorDetails.put("url", request.getRequestURI());
        errorDetails.put("method", request.getMethod());
        errorDetails.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
