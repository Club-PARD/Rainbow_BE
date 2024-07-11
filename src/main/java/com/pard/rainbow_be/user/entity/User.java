package com.pard.rainbow_be.user.entity;

import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
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
public class User extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "password",length = 30)
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters")
    private String password;

    @Builder.Default
    @Column(name = "public_check",nullable = false)
    private Boolean publicCheck = false;

    public static User localToEntity(UserDto.Create dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .petName(dto.getPetName())
                .build();
    }

    public void updateBoolean(Boolean publicCheck){
        this.publicCheck = publicCheck;
    }

    public void localToUpdate(String name, String petName){
        this.name = name;
        this.petName = petName;
    }
}
