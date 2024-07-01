package com.pard.rainbow_be.user.entity;

import com.pard.rainbow_be.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String petName;

    @CreationTimestamp()
    @Column(updatable = false)
    private LocalDateTime createDate;

    public static User localToEntity(UserDto.Create dto){
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .petName(dto.getPetName())
                .build();
    }

    public void update(String name){
        this.name = name;
    }

    public void localToUpdate(String name, String petName){
        this.name = name;
        this.petName = petName;
    }
}
