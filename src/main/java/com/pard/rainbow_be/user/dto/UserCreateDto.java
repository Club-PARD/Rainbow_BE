package com.pard.rainbow_be.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String name;
    private String email;
    private String password;
    private String petName;
}
