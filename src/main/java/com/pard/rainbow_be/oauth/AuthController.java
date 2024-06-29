package com.pard.rainbow_be.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }
}