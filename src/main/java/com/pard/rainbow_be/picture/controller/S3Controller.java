package com.pard.rainbow_be.picture.controller;

import com.pard.rainbow_be.picture.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class S3Controller {
    private final S3Service s3Service;
    @PostMapping("/file/profile")
    @Operation(summary = "사진 업로드", description = "사진을 올리시변 변형시켜 url 값만 드립니다ㅎ")
    public String uploadProfileImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        log.info("\uD83D\uDCCD upload picture");
        return s3Service.uploadProfile(multipartFile);
    }
}
