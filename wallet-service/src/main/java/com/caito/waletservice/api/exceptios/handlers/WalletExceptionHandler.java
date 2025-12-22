package com.caito.waletservice.api.exceptios.handlers;

import com.caito.waletservice.api.exceptios.customs.WalletException;
import com.pp.commonsservice.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/*
 * WalletExceptionHandler handles WalletException globally and constructs a standardized error response.
 *
 * @author Caito
 *
 */
@RestControllerAdvice
public class WalletExceptionHandler {
    @ExceptionHandler(WalletException.class)
    protected ResponseEntity<ErrorResponse> handleWalletException(WalletException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build());
    }
}
