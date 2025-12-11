package com.caito.merchantservice.configs.security.jwt;

import com.caito.merchantservice.configs.security.keys.KeyUtils;
import com.caito.merchantservice.persistence.entities.MerchantUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.*;
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
    private PrivateKey privateKey = KeyUtils.generateKeyPair().getPrivate();
    private PublicKey publicKey = KeyUtils.generateKeyPair().getPublic();

    @PostConstruct
    public void init() {
        generateKeyPair();
    }

    /**
     * Generates a JWT token for the given authenticated user.
     *
     * @param authentication the authentication object containing user details
     * @return a JWT token as a String
     */
    public String generateToken(Authentication authentication) {
        MerchantUser user = (MerchantUser) authentication.getPrincipal();
        assert user != null;
        String apiKey = user.getMerchant().getApiKey();
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .claim("roles", getRoles(user))
                .claim("api_key", apiKey)
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

    private void generateKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            this.privateKey = kp.getPrivate();
            this.publicKey = kp.getPublic();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
