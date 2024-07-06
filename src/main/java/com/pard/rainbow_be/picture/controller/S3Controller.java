package com.pard.rainbow_be.picture.controller;

import com.pard.rainbow_be.picture.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class S3Controller {
    private final S3Service s3Service;
    @PostMapping("/file/profile")
    public String uploadProfileImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {

        return s3Service.uploadProfile(multipartFile);
    }
}
