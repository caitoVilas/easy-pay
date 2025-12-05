package com.caito.merchantservice.api.exceptions;

import com.pp.commonsservice.exceptions.UnauthorizedException;
import com.pp.commonsservice.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/*
 * UnauthorizedExceptionHandler handles UnauthorizedException globally and constructs a standardized error response.
 *
 * @author Caito
 *
 */
@RestControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> badRequestHandler(UnauthorizedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build());
    }
}
