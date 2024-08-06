package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.oauth.service.AuthService;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import com.pard.rainbow_be.user.service.UserService;
import com.pard.rainbow_be.oauth.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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
    @Operation(summary = "구글 로그인", description = "구글 로그인 후 이메일 반환")
    public Map<String, Object> googleLogin(@RequestBody Map<String, Object> userData, HttpServletResponse response) {
        String email = (String) userData.get("email");

        Map<String, Object> userInfo = authService.saveOrUpdateUser(email);

        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
        setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));
        log.info("\uD83D\uDCCD gmail login");
        return userInfo;
    }

    @GetMapping("/login")
    @ResponseBody
    @Operation(summary = "로컬 로그인", description = "이메일과 페스워드로 로그인이 될수도? 안될 수도?")
    public Map<String, Object> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        boolean isAuthenticated = userService.validateUser(email, password);
        if (isAuthenticated) {
            log.info("\uD83D\uDCCD 로그인 성공: " + email);
            User user = userRepo.findByEmail(email).orElseThrow();

            String accessToken = jwtUtil.generateAccessToken(email);
            String refreshToken = jwtUtil.generateRefreshToken(email);

            setCookie(response, "access_token", accessToken, (int) (JwtUtil.ACCESS_EXPIRATION_TIME / 1000));
            setCookie(response, "refresh_token", refreshToken, (int) (JwtUtil.REFRESH_EXPIRATION_TIME / 1000));

            Map<String, Object> result = new HashMap<>();
            result.put("user_id", user.getId().toString());
            result.put("email", user.getEmail());
            result.put("name", user.getName());

            return result;
        } else {
            log.info("\uD83D\uDCCD  로그인 실패: " + email);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Map.of("error", "Invalid email or password");
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "access_token 유효성 감사 ", description = "access_token 이 있는 지 확인하고 정보 전달")
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
    @Operation(summary = "access_token 갱신", description = "새로운 액세스 토큰을 쿠키로 설정")
    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .findFirst();

        if (refreshTokenCookie.isPresent()) {
            try {
                Claims claims = jwtUtil.validateToken(refreshTokenCookie.get().getValue());
                String newAccessToken = jwtUtil.generateAccessToken(claims.getSubject());

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