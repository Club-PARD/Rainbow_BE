package com.pard.rainbow_be.post.service;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
//import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.entity.Post;
import com.pard.rainbow_be.post.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;

    public void createPost(PostCreateDTO postCreateDTO){
        postRepo.save(Post.toEntity(postCreateDTO));
    }

//    public List<PostReadDTO> readAll(){
//        return postRepo.findAll()
//                .stream()
//                .map(PostReadDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    public void createPost(PostCreateDTO postCreateDTO){
//        postRepo.save(postCreateDTO.toEntity());
//    }
//
//    public void updatePost(Long uid, PostUpdateDTO postUpdateDTO){
//        Post post = postRepo.findById(uid).orElseThrow();
//        post = postUpdateDTO.toDTO();
//        postRepo.save(post);
//    }
//
//    public void deleteByPid(Long pid){postRepo.deleteByPid(pid);}
//
//    public PostReadDTO readByPid(Long pid){return postRepo.findById(pid).get().toDTO();}
//
//    public List<PostReadDTO> readById(Integer pid){
//        return postRepo.findByPid(pid)
//                .stream()
//                .map(PostReadDTO::new)
//                .collect(Collectors.toList());
//    }
}
