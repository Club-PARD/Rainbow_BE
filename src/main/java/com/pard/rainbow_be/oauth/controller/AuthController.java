package com.pard.rainbow_be.oauth.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pard.rainbow_be.oauth.service.AuthService;
import com.pard.rainbow_be.oauth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService JwtService;
    private final AuthService authService;

    @Value("${google.clientId}")
    private String googleClientId;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.JwtService = jwtService;
        this.authService = authService;
    }

    private void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

//    @PostMapping("/googleLogin")
//    @Operation(summary = "구글 로그인", description = "구글 로그인 후 이메일 반환")
//    public Map<String, Object> googleLogin(@RequestBody Map<String, Object> userData, HttpServletResponse response) {
//        String email = (String) userData.get("email");
//
//        Map<String, Object> userInfo = authService.saveOrUpdateUser(email);
//
//        String accessToken = jwtUtil.generateAccessToken(email);
//        String refreshToken = jwtUtil.generateRefreshToken(email);
//
//        setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
//        setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));
//        log.info("\uD83D\uDCCD gmail login");
//        return userInfo;
//
//    }

    @PostMapping("/googleLogin")
    @Operation(summary = "구글 로그인", description = "구글 로그인 후 이메일 반환")
    public Map<String, Object> googleLogin(@RequestBody Map<String, Object> userData, HttpServletResponse response) {
        String idTokenString = (String) userData.get("token");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            Map<String, Object> userInfo = authService.saveOrUpdateUser(email);

            String accessToken = JwtService.generateAccessToken(email);
            String refreshToken = JwtService.generateRefreshToken(email);

            setCookie(response, "access_token", accessToken, (int) (JwtService.getAccessTokenExpiration() / 1000));
            setCookie(response, "refresh_token", refreshToken, (int) (JwtService.getRefreshTokenExpiration() / 1000));
            log.info("\uD83D\uDCCD gmail login");
            userInfo.put("accessToken", accessToken);
            userInfo.put("refreshToken", refreshToken);
            return userInfo;
        } else {
            throw new RuntimeException("Invalid ID token");
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "access_token 유효성 감사 ", description = "access_token 이 있는 지 확인하고 정보 전달")
    public String validateToken(HttpServletRequest request) {
        Optional<Cookie> accessTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst();

        if (accessTokenCookie.isPresent()) {
            Claims claims = JwtService.validateToken(accessTokenCookie.get().getValue());
            if (claims != null) {
                return "Token is valid for user: " + claims.getSubject();
            } else {
                return "Invalid token";
            }
        } else {
            return "Access token not found";
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "access_token 갱신", description = "새로운 액세스 토큰을 쿠키로 설정")
    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .findFirst();

        if (refreshTokenCookie.isPresent()) {
            try {
                Claims claims = JwtService.validateToken(refreshTokenCookie.get().getValue());
                String newAccessToken = JwtService.generateAccessToken(claims.getSubject());

                Cookie newAccessCookie = new Cookie("access_token", newAccessToken);
                newAccessCookie.setHttpOnly(true);
                newAccessCookie.setSecure(true);
                newAccessCookie.setPath("/");
                newAccessCookie.setMaxAge((int) (JwtService.getAccessTokenExpiration() / 1000));

                response.addCookie(newAccessCookie);

                return "Access token refreshed successfully";
            } catch (Exception e) {
                return "Invalid refresh token";
            }
        } else {
            return "Refresh token not found";
        }
    }

    //    Local login 을 하기에는 이메일 인증이나 다른 인증 서비스가 없으므로 실사용하기 어려움
//    추후 사용할 예정
//    @GetMapping("/login")
//    @ResponseBody
//    @Operation(summary = "로컬 로그인", description = "이메일과 페스워드로 로그인이 될수도? 안될 수도?")
//    public Map<String, Object> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
//        boolean isAuthenticated = userService.validateUser(email, password);
//        if (isAuthenticated) {
//            log.info("\uD83D\uDCCD 로그인 성공: " + email);
//            User user = userRepo.findByEmail(email).orElseThrow();
//
//            String accessToken = jwtUtil.generateAccessToken(email);
//            String refreshToken = jwtUtil.generateRefreshToken(email);
//
//            setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
//            setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));
//
//            Map<String, Object> result = new HashMap<>();
//            result.put("user_id", user.getId().toString());
//            result.put("email", user.getEmail());
//            result.put("name", user.getName());
//
//            return result;
//        } else {
//            log.info("\uD83D\uDCCD  로그인 실패: " + email);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return Map.of("error", "Invalid email or password");
//        }
//    }
}