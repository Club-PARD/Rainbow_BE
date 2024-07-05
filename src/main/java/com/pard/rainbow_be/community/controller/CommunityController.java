package com.pard.rainbow_be.community.controller;


import com.pard.rainbow_be.community.dto.CommunityDto;
import com.pard.rainbow_be.community.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/community")
    @Operation(summary = "Community 사진 보여주가", description = "해당 유저가 게시물을 만드는 메서드")
    public List<CommunityDto.Read> readsTheLatestPost(){
        log.info("📍 커뮤니티 란 나왔당");
        return communityService.readsTheLatestPost();
    }
}
