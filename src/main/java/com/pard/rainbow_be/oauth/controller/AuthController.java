package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.oauth.service.AuthService;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import com.pard.rainbow_be.user.service.UserService;
import com.pard.rainbow_be.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final UserService userService;
    private final UserRepo userRepo;

    public AuthController(JwtUtil jwtUtil, AuthService authService ,UserService userService, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    private void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    @PostMapping("/googleLogin")
    public Map<String, Object> googleLogin(@RequestBody Map<String, Object> userData, HttpServletResponse response) {
        String email = (String) userData.get("email");

        Map<String, Object> userInfo = authService.saveOrUpdateUser(email);

        // 액세스 토큰 및 리프레시 토큰 발급
        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        // 액세스 토큰을 쿠키로 설정
        setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
        setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));

        return userInfo;
    }

    // 로컬 연결이 끝나면 연결할 예정 // jwt를 이용한 localLogin 구현
//    @GetMapping("/login")
//    @ResponseBody
//    public Map<String, Object> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
//        boolean isAuthenticated = userService.validateUser(email, password);
//        if (isAuthenticated) {
//            log.info("로그인 성공: " + email);
//            User user = userRepo.findByEmail(email).orElseThrow();
//
//            // 액세스 토큰 및 리프레시 토큰 발급
//            String accessToken = jwtUtil.generateAccessToken(email);
//            String refreshToken = jwtUtil.generateRefreshToken(email);
//
//            setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
//            setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));
//
//            // 사용자 정보 반환
//            Map<String, Object> userInfo = Map.of(
//                    "id", user.getId(),
//                    "email", user.getEmail()
//            );
//            return userInfo;
//        } else {
//            log.info("로그인 실패: " + email);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return Map.of("error", "Invalid email or password");
//        }
//    }
    @GetMapping("/login")
    @ResponseBody
    public UUID login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.validateUser(email, password);
        if (isAuthenticated) {
            log.info("로그인 성공: " + email);
            User user = userRepo.findByEmail(email).orElseThrow();
            return user.getId();
        } else {
            log.info("로그인 실패: " + email);
        }
        return null;
    }

    @GetMapping("/validate")
    public String validateToken(HttpServletRequest request) {
        Optional<Cookie> accessTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst();

        if (accessTokenCookie.isPresent()) {
            Claims claims = jwtUtil.validateToken(accessTokenCookie.get().getValue());
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
    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .findFirst();

        if (refreshTokenCookie.isPresent()) {
            try {
                Claims claims = jwtUtil.validateToken(refreshTokenCookie.get().getValue());
                String newAccessToken = jwtUtil.generateAccessToken(claims.getSubject());

                // 새로운 액세스 토큰을 쿠키로 설정
                Cookie newAccessCookie = new Cookie("access_token", newAccessToken);
                newAccessCookie.setHttpOnly(true);
                newAccessCookie.setSecure(true);
                newAccessCookie.setPath("/");
                newAccessCookie.setMaxAge((int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));

                response.addCookie(newAccessCookie);

                return "Access token refreshed successfully";
            } catch (Exception e) {
                return "Invalid refresh token";
            }
        } else {
            return "Refresh token not found";
        }
    }
}