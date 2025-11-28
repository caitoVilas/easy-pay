package com.caito.merchantservice.configs.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pp.commonsservice.logs.WriteLog;
import com.pp.commonsservice.models.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * JwtEntryPoint handles unauthorized access attempts to protected resources.
 * It implements the AuthenticationEntryPoint interface to send a 401 Unauthorized
 * response with a structured error message when authentication fails.
 *
 * @author caito
 *
 */
@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException
                                                       authException) throws IOException, ServletException {
        final String msg = "Unauthorized";
        var res = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(msg)
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build();
        log.error(WriteLog.logError("--> Unauthorized"));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String apiError = mapper.writeValueAsString(res);
        response.getWriter().write(apiError);
    }
}
