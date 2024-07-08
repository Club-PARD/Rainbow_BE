package com.pard.rainbow_be.user.controller;


import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    //localToSignUp
    @PostMapping({"/register"})
    @Operation(summary = "유저 등록", description = "여기서 쓰시면 됩니다.")
    public ResponseEntity<String> createUser(@RequestBody UserDto.Create dto) {
        if (userService.userExists(dto.getEmail())) {
            return ResponseEntity.status(400).body("Username is already taken");
        }

        userService.createUser(dto);
        log.info("📍make User");
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/find/email/{email}")
    @Operation(summary = "이메일로 유저 ID 검색",description = "이메일을 통해 DB 내 해당 유저 검색")
    public UUID readByEmail(@PathVariable String email){
        log.info("📍ReadByEmail");
        return userService.readByEmail(email);
    }

    @GetMapping("/find/pet/{userId}")
    @Operation(summary = "userID로 반려동물 이름 검색",description = "ID를 통해 반려동물 이름 검색")
    public String readPetNameByID(@PathVariable UUID userId){
        log.info("📍readPetNameByID");
        return userService.readPetNameByID(userId);
    }

    @GetMapping("/find/id/{userId}")
    @Operation(summary = "userID로 유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
    public UserDto.Read readById(@PathVariable UUID userId){
        log.info("📍ReadByID");
        return userService.readById(userId);
    }

    @PatchMapping("/google/register/{userId}")
    @Operation(summary = "이름과 반려동물 이름 업데이트", description = "ID를 통해 해당 유저의 정보 변경")
    public void updateUser(@PathVariable UUID userId, @RequestBody UserDto.Update dto){
        userService.updateUser(userId, dto.getNickName(), dto.getPetName());
        log.info("📍유저가 업데이트 되었어요.");
    }

    @PatchMapping("/update/publicCheck/{userId}")
    @Operation(summary = "공개, 비공개 설정", description = "값을 입력하면 바로 그값 들어게 만들었어요! (넘겨주는 변수 변경 가능!)")
    public boolean updatePublic(@PathVariable UUID userId, @RequestParam boolean check){
        log.info("📍공개, 비공개");
        return userService.updatePublic(userId, check);
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "유저 삭제", description = "User Id를 주면 해당 user 삭제")
    public void deleteUser(@PathVariable UUID userId){
        log.info("📍유저 삭제");
        userService.deleteUser(userId);
    }
}
