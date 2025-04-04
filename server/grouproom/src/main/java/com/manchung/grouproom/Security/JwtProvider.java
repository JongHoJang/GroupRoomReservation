package com.manchung.grouproom.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpirationMs;

    private static final String CLAIM_USER_ID = "userId";
    public static final String BEARER_PREFIX = "Bearer ";

    public String createAccessToken(Integer userId) {
        String token = JWT.create()
                .withSubject("AccessToken")
                .withClaim(CLAIM_USER_ID, userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .sign(Algorithm.HMAC256(secretKey));

        log.info("[JWT] Access token created for userId={}", userId);
        return token;
    }

    public String createRefreshToken(Integer userId) {
        String token = JWT.create()
                .withSubject("RefreshToken")
                .withClaim(CLAIM_USER_ID, userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .sign(Algorithm.HMAC256(secretKey));

        log.info("[JWT] Refresh token created for userId={}", userId);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);

            boolean isValid = jwt.getExpiresAt().after(new Date());
            log.info("[JWT] Token validation result: {}", isValid);
            return isValid;
        } catch (JWTVerificationException e) {
            log.warn("[JWT] Invalid or expired token: {}", e.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        Long userId = decodedJWT.getClaim(CLAIM_USER_ID).asLong();
        log.info("[JWT] Extracted userId from token: {}", userId);
        return userId;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(7);
            log.info("[JWT] Bearer token resolved from header");
            return token;
        }
        log.info("[JWT] No valid bearer token found in header");
        return null;
    }
}