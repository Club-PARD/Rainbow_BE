package com.pard.rainbow_be.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReadDto {
    private UUID id;
    private String name;
    private String email;
    private String petName;
    private boolean publicCheck;

    public UserReadDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.petName = user.getPetName();
        this.publicCheck = user.getPublicCheck();
    }
}
