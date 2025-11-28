package com.caito.merchantservice.services.impl;

import com.caito.merchantservice.api.models.requests.AuthRequest;
import com.caito.merchantservice.api.models.responses.AuthResponse;
import com.caito.merchantservice.configs.security.jwt.JwtProvider;
import com.caito.merchantservice.services.contracts.AuthService;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthService interface.
 * Provides authentication services such as user login.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIml implements AuthService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest request) {
        log.info(WriteLog.logInfo("--> Login service"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthResponse.builder()
                .accessToken(jwtProvider.generateToken(authentication))
                .build();
    }
}
