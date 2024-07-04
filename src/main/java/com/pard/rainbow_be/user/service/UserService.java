package com.pard.rainbow_be.user.service;

import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public void createUser(UserDto.Create dto) {
        this.userRepo.save(User.localToEntity(dto));
    }

    public UserDto.Read readById(UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        return new UserDto.Read(user);
    }

    public void updateUser(UUID id, String name, String petName) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (userPresent(optionalUser)) {
            User user = optionalUser.orElseThrow();
            user.localToUpdate(name, petName);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public boolean validateUser(String email, String password) {
        Optional<User> user = userRepo.findByEmail(email);
        return (userPresent(user) && user.orElseThrow().getPassword().equals(password));
    }


    public boolean userExists(String email) {
        return userRepo.existsByEmail(email);
    }
    private boolean userPresent(Optional<User> user){
        return user.isPresent();
    }

    public User saveOrUpdate(User user) {
        return userRepo.save(user);
    }
}
