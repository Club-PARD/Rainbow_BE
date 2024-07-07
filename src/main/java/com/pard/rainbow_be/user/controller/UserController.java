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

    @GetMapping("/find/{userId}")
    @Operation(summary = "유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
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
}
