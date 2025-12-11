package com.caito.merchantservice.api.exceptions;

import com.pp.commonsservice.exceptions.BadRequestException;
import com.pp.commonsservice.models.ErrorsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/*
 * BadRequestExceptionHandler handles BadRequestException globally and constructs a standardized error response.
 *
 * @author Caito
 *
 */
@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorsResponse> badRequestHandler(BadRequestException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(ErrorsResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .messages(e.getMessages())
                        .method(request.getMethod())
                        .path(request.getRequestURL().toString())
                .build());
    }
}
