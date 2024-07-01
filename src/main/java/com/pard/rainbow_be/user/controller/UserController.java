package com.pard.rainbow_be.user.controller;


import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    //localToSignUp
    @PostMapping({""})
    @Operation(summary = "유저 등록", description = "여기서 쓰시면 됩니다.")
    public void createUser(@RequestBody UserDto.Create dto) {
        this.userService.createUser(dto);
        log.info("📍make User");
    }

    @GetMapping("/find/{userId}")
    @Operation(summary = "유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
    public UserDto.Read readById(@PathVariable UUID userId){
        log.info("📍ReadByID");
        return userService.readById(userId);
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "이름과 반려동물 이름 업데이트", description = "ID를 통해 해당 유저의 정보 변경")
    public void updateUser(@PathVariable UUID userId, @RequestBody UserDto.Update dto){
        userService.updateUser(userId, dto.getName(), dto.getPetName());
        log.info("유저가 업데이트 되었어요.");
    }


}
