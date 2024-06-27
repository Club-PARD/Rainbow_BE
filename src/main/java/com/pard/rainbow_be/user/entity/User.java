package com.pard.rainbow_be.user.entity;

import com.pard.rainbow_be.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    private String email;
    private String name;

    public static User toEntity(UserDto.Create dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}
