package com.pard.rainbow_be.oauth.service;

import com.pard.rainbow_be.oauth.repo.RefreshTokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    private final RefreshTokenRepo refreshTokenRepo;
    private Key key;

    public JwtService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Value("${jwt.secret}")
    public void setKey(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Getter
    @Value("${jwt.access.token.expiration}")
    private Long accessTokenExpiration;

    @Getter
    @Value("${jwt.refresh.token.expiration}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(String email) {
        return createToken(email, accessTokenExpiration);
    }

    private String createToken(String email, Long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        log.info("\uD83D\uDCCD Create token");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            log.info("\uD83D\uDCCD Validate token");
            return extractAllClaims(token);
        } catch (Exception e) {
            return null;
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        log.info("\uD83D\uDCCD Extract claims");
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("\uD83D\uDCCD Extract all claims");
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        log.info("\uD83D\uDCCD IsTokenExpired");
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
