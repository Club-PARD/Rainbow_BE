package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.oauth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userDetailsService.authenticate(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}