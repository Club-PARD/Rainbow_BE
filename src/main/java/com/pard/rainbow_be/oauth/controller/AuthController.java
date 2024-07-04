package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/loginForm")
    public String loginForm(){
        log.info("로그인 되었엉");
        return "loginForm";
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