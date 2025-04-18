package com.example.FireWatch03.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.FireWatch03.domain.models.UserAutenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    private static final ZoneOffset ZONE = ZoneOffset.of("-03:00");

    public static class TokenGenerationException extends RuntimeException {
        public TokenGenerationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public String generateToken(UserAutenticator user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenGenerationException("Failed to generate token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        int hours = "staging".equals(activeProfile) ? 8 : 2;
        return LocalDateTime.now().plusHours(hours).toInstant(ZONE);
    }
}