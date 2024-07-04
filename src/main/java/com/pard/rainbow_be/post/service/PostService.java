package com.pard.rainbow_be.post.service;

import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
//import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
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

    public List<PostReadDTO> readAll(){
        return postRepo.findAll()
                .stream()
                .map(PostReadDTO::new)
                .collect(Collectors.toList());
    }

    public PostReadDTO findById(Long postId){
        return new PostReadDTO(postRepo.findById(postId).orElseThrow());
    }


    public void updateById(Long postId, PostUpdateDTO postUpdateDTO){
        Post post = postRepo.findById(postId).get();
        post.update(postUpdateDTO);
        postRepo.save(post);
    }

    //delete member by id
    public void deleteById(Long postId) {
        postRepo.deleteById(postId);
    }

}
