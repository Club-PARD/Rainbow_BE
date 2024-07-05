package com.pard.rainbow_be.community.service;

import com.pard.rainbow_be.community.dto.CommunityDto;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.post.repo.PostRepo;
import com.pard.rainbow_be.post.service.PostService;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import com.pard.rainbow_be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final PostService postService;
    private final PostRepo postRepo;
    private final UserRepo userRepo;


//    public List<CommunityDto.Read> readsTheLatestPost(){
//        List<User> users = userRepo.findAll();
//
//        List<UUID> userIds = users.stream()
//                .map(User::getId) // User 객체에서 아이디만 추출
//                .toList();
//
//    }
}
