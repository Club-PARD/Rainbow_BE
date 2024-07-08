package com.pard.rainbow_be.user.entity;

import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.util.BaseTimeEntity;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String petName;

    @Column
    private String password;

    @Builder.Default
    @Column(name = "public_check", nullable = false)
    private Boolean publicCheck = false;

    public static User localToEntity(UserDto.Create dto){
        return User.builder()
                .nickName(dto.getNickName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .petName(dto.getPetName())
                .build();
    }

    public void update(String nickName){
        this.nickName = nickName;
    }

    public void updateBoolean(Boolean publicCheck){
        this.publicCheck = publicCheck;
    }

    public void localToUpdate(String nickName, String petName){
        this.nickName = nickName;
        this.petName = petName;
    }
}
