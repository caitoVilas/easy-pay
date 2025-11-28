package com.caito.merchantservice.configs.security.jwt;

import com.pp.commonsservice.logs.WriteLog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter is a custom filter that intercepts incoming HTTP requests to validate JWT tokens.
 * It extracts the token from the Authorization header, validates it, and sets the authentication
 * in the SecurityContext if the token is valid.
 *
 * @author caito
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                                                                         throws ServletException, IOException {
        String token = extrackToken(request);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                var userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken  auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error(WriteLog.logError("Error al validar el token JWT: " + e.getMessage()));
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request
     * @return the JWT token as a String, or null if not present
     */
    private String extrackToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
