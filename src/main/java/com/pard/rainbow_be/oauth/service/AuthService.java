package com.pard.rainbow_be.oauth.service;


import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

}
