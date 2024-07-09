package com.pard.rainbow_be.oauth.service;


import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Map<String, Object> saveOrUpdateUser(String email) {
        User user = userService.createOrUpdateUser(email);
        log.info("들어갔냐??");

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", user.getId().toString());
        result.put("email", user.getEmail());

        return result;
    }
}
