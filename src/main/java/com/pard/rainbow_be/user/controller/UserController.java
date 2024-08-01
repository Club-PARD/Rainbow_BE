package com.pard.rainbow_be.user.controller;


import com.pard.rainbow_be.user.dto.UserCreateDto;
import com.pard.rainbow_be.user.dto.UserReadDto;
import com.pard.rainbow_be.user.dto.UserUpdateDto;
import com.pard.rainbow_be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto dto) {
        log.info("\uD83D\uDCCD make Post");
        userService.createUser(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/email/{email}")
    @Operation(summary = "이메일로 유저 ID 검색",description = "이메일을 통해 DB 내 해당 유저 검색")
    public ResponseEntity<?> readByEmail(@PathVariable String email){
        log.info("\uD83D\uDCCD read User by using email");
        if (email == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UUID userId = userService.readByEmail(email);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping("/find/pet/{ownerId}")
    @Operation(summary = "userID로 반려동물 이름 검색",description = "ID를 통해 반려동물 이름 검색")
    public ResponseEntity<?> readPetNameByID(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get pet name by using ownerId");

        if (ownerId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String petName = userService.readPetNameByID(ownerId);
        return new ResponseEntity<>(petName, HttpStatus.OK);
    }

    @GetMapping("/find/id/{ownerId}")
    @Operation(summary = "userID로 유저 검색",description = "ID를 통해 DB 내 해당 유저 검색")
    public ResponseEntity<?> readById(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD get user by using ownerId");

        if (ownerId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserReadDto dto = userService.readById(ownerId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/google/register/{ownerId}")
    @Operation(summary = "구글 유저 등록 및 이름 반려동물 이름 바꾸기.", description = "ID를 통해 해당 유저의 정보 변경")
    public ResponseEntity<?> updateUser(@PathVariable UUID ownerId, @RequestBody UserUpdateDto dto){
        log.info("\uD83D\uDCCD register google user");
        userService.updateUser(ownerId, dto.getName(), dto.getPetName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/publicCheck/{ownerId}")
    @Operation(summary = "공개, 비공개 설정", description = "값을 입력하면 바로 그값 들어게 만들었어요! (넘겨주는 변수 변경 가능!)")
    public ResponseEntity<?> updatePublic(@PathVariable UUID ownerId, @RequestParam boolean check){
       log.info("\uD83D\uDCCD patch public user");
       boolean publicCheck = userService.updatePublic(ownerId, check);

       return new ResponseEntity<>(publicCheck, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ownerId}")
    @Operation(summary = "유저 삭제", description = "User Id를 주면 해당 user 삭제")
    public ResponseEntity<?> deleteUser(@PathVariable UUID ownerId){
        log.info("\uD83D\uDCCD delete user");
        userService.deleteUser(ownerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
