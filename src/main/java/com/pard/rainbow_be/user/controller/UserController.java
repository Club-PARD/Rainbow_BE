package com.pard.rainbow_be.user.controller;


import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping({""})
    @Operation(summary = "유저 등록", description = "여기서 쓰시면 됩니다.")
    public String createUser(@RequestBody UserDto.Create dto) {
        this.userService.createUser(dto);
        return "추가됨";
    }

    @GetMapping("/find/{userId}")
    @Operation(summary = "유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
    public UserDto.Read readById(@PathVariable UUID userId){ return userService.readById(userId); }

}
