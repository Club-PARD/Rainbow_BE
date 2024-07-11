package com.pard.rainbow_be.user.controller;


import com.pard.rainbow_be.user.dto.UserDto;
import com.pard.rainbow_be.user.service.UserService;
import com.pard.rainbow_be.util.ResponseDTO;
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
    public void createUser(@RequestBody UserDto.Create dto) {
        log.info("\uD83D\uDCCD make Post");
        userService.createUser(dto);
    }

    @GetMapping("/find/email/{email}")
    @Operation(summary = "이메일로 유저 ID 검색",description = "이메일을 통해 DB 내 해당 유저 검색")
    public UUID readByEmail(@PathVariable String email){
        log.info("\uD83D\uDCCD read User by using email");
        return userService.readByEmail(email);
    }

    @GetMapping("/find/pet/{ownerId}")
    @Operation(summary = "userID로 반려동물 이름 검색",description = "ID를 통해 반려동물 이름 검색")
    public String readPetNameByID(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get pet name by using ownerId");
        return userService.readPetNameByID(ownerId);
    }

    @GetMapping("/find/id/{ownerId}")
    @Operation(summary = "userID로 유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
    public UserDto.Read readById(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get user by using ownerId");
        return userService.readById(ownerId);
    }

    @PatchMapping("/google/register/{ownerId}")
    @Operation(summary = "구글 유저 등록 및 이름 반려동물 이름 바꾸기.", description = "ID를 통해 해당 유저의 정보 변경")
    public void updateUser(@PathVariable UUID ownerId, @RequestBody UserDto.Update dto){
        log.info("\uD83D\uDCCD register google user");
        userService.updateUser(ownerId, dto.getName(), dto.getPetName());
    }

    @PatchMapping("/update/publicCheck/{ownerId}")
    @Operation(summary = "공개, 비공개 설정", description = "값을 입력하면 바로 그값 들어게 만들었어요! (넘겨주는 변수 변경 가능!)")
    public boolean updatePublic(@PathVariable UUID ownerId, @RequestParam boolean check){
       log.info("\uD83D\uDCCD patch public user");
        return userService.updatePublic(ownerId, check);
    }

    @DeleteMapping("/delete/{ownerId}")
    @Operation(summary = "유저 삭제", description = "User Id를 주면 해당 user 삭제")
    public void deleteUser(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD delete user");
        userService.deleteUser(ownerId);
    }
}
