package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.oauth.dto.OAuthAttributes;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

        @PostMapping("/loginForm")
        public ResponseEntity<String> loginForm(@RequestBody Map<String, Object> userAttributes) {
            log.info("로그인 폼 호출됨");

            OAuthAttributes attributes = OAuthAttributes.of("email", userAttributes);
            String email = attributes.getEmail();

            if (userService.userExists(email)) {
                User user = userService.saveOrUpdate(attributes.toEntity());
                httpSession.setAttribute("user", user);
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid email");
            }
        }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.validateUser(email, password);
        if (isAuthenticated) {
            log.info("로그인 성공: " + email);
            return ResponseEntity.ok(true);
        } else {
            log.info("로그인 실패: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}