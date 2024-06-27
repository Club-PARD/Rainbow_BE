package com.pard.rainbow_be.user.service;

import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public UserDto.Read readById(UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        return new UserDto.Read(user);
    }


}
