package com.caito.merchantservice.configs.security.jwt;

import com.caito.merchantservice.configs.security.keys.KeyUtils;
import com.caito.merchantservice.persistence.entities.MerchantUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

/**
 * JwtProvider is responsible for generating JWT tokens for authenticated users.
 * It uses the Jwts library to create tokens that include the user's email and roles,
 * along with an expiration time.
 *
 * @author caito
 *
 */
@Component
@Slf4j
public class JwtProvider {
    private static final long EXPIRATION_TIME = 3600000;
    private final PrivateKey privateKey = KeyUtils.generateKeyPair().getPrivate();
    private final PublicKey publicKey = KeyUtils.generateKeyPair().getPublic();

    /**
     * Generates a JWT token for the given authenticated user.
     *
     * @param authentication the authentication object containing user details
     * @return a JWT token as a String
     */
    public String generateToken(Authentication authentication) {
        MerchantUser user = (MerchantUser) authentication.getPrincipal();
        assert user != null;
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .claim("roles", getRoles(user))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    /**
     * Extracts the username (email) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username (email) extracted from the token
     */
    public String getUsernameFromToken(String token) {
        Claims parser = Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return parser.getSubject();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }



    /**
     * Extracts the roles from the MerchantUser and returns them as a list of strings.
     *
     * @param user the MerchantUser object
     * @return a list of role names
     */
    private List<String> getRoles(MerchantUser user) {
        return user.getRoles()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

}
