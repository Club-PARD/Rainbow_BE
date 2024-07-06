package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.oauth.dto.OAuthAttributes;
import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
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
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HttpSession httpSession;

        @PostMapping("/loginForm")
        @ResponseBody
        public ResponseEntity<String> loginForm(@RequestBody Map<String, Object> userAttributes) {
            log.info("📍로그인 폼 호출됨");

            OAuthAttributes attributes = OAuthAttributes.of("email", userAttributes);
            String email = attributes.getEmail();

            if (userService.userExists(email)) {
                User user = userService.saveOrUpdate(attributes.toEntity());
                httpSession.setAttribute("user", user);
                log.info("📍로그인 성공: " + email);
                return ResponseEntity.ok("Login successful");
            } else {
                userService.saveOrUpdate(attributes.toEntity());
                log.info("📍회원가입 및 로그인 성공: " + email);
                return ResponseEntity.status(401).body("Invalid email");
            }
        }

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
}