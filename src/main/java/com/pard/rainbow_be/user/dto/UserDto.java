package com.pard.rainbow_be.user.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserDto {
    @Getter
    @Setter
    public static class Create{
        private String name;
        private String email;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Read{
        private UUID id;
        private String name;
        private String email;

        public Read(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }
}