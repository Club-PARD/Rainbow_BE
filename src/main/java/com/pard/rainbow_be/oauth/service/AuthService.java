package com.pard.rainbow_be.oauth.service;


import com.pard.rainbow_be.oauth.entity.RefreshToken;
import com.pard.rainbow_be.oauth.repo.RefreshTokenRepo;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AuthService {

    private final UserService userService;
    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtService jwtService;

    public AuthService(UserService userService, RefreshTokenRepo refreshTokenRepo, JwtService jwtService) {
        this.userService = userService;
        this.refreshTokenRepo = refreshTokenRepo;
        this.jwtService = jwtService;
    }

    public Map<String, Object> saveOrUpdateUser(String email) {
        User user = userService.createOrUpdateUser(email);
        log.info("들어갔냐??");

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", user.getId().toString());
        result.put("email", user.getEmail());
        result.put("name", user.getName());

        return result;
    }


    public String createRefreshToken(String email) {

        deleteExistingRefreshTokenByEmail(email);

        RefreshToken refreshToken =  RefreshToken.builder()
                .email(email)
                .token(UUID.randomUUID().toString())
                .expirationDate(new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpiration()))
                .build();
        refreshTokenRepo.save(refreshToken);
        log.info("\uD83D\uDCCD Created refresh token: {}", refreshToken);
        return refreshToken.getToken();
    }

    public void deleteRefreshTokenByToken(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken).ifPresent(refreshTokenRepo::delete);
    }

    private void deleteExistingRefreshTokenByEmail(String email) {
        Optional<RefreshToken> existingToken = refreshTokenRepo.findByEmail(email);
        existingToken.ifPresent(refreshTokenRepo::delete);
    }

    public String generateNewAccessToken(String refreshToken) {
        RefreshToken token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        log.info("\uD83D\uDCCD make Access token");
        if (token.getExpirationDate().before(new Date())) {
            throw new RuntimeException("Refresh token expired");
        }
        return jwtService.generateAccessToken(token.getEmail());
    }

}
