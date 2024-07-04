package com.pard.rainbow_be.oauth.dto;


import com.pard.rainbow_be.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private final String email;

    public SessionUser(User user) {
        this.email = user.getEmail();
    }
}