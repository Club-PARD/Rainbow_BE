package com.pard.rainbow_be.oauth.controller;

import com.pard.rainbow_be.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.validateUser(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}