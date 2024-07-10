package com.pard.rainbow_be.post.service;

import com.pard.rainbow_be.post.dto.CommunityReadDto;
import com.pard.rainbow_be.post.dto.PostCreateDTO;
import com.pard.rainbow_be.post.dto.PostReadDTO;
import com.pard.rainbow_be.post.dto.PostUpdateDTO;
import com.pard.rainbow_be.post.entity.Post;
import com.pard.rainbow_be.post.repo.PostRepo;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.question.repo.QuestionRepo;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import com.pard.rainbow_be.userToQuestion.service.UserQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final UserQuestionService userQuestionService;
    public void createPost(PostCreateDTO postCreateDTO, UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        userQuestionService.answeredQuestion(userId, postCreateDTO.getPostTitle(), true);
        postRepo.save(Post.toEntity(postCreateDTO, user));
    }

    public List<PostReadDTO> readAll(UUID userId) {
        return postRepo.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "createdTime"))
                .stream()
                .map(PostReadDTO::new)
                .collect(Collectors.toList());
    }

    public PostReadDTO findById(Long postId){
        return new PostReadDTO(postRepo.findById(postId).orElseThrow());
    }

    public Integer countByUserId(UUID userId){
        List<Post> posts = postRepo.findAllByUserId(userId)
                .stream()
                .map(PostReadDTO::new)
                .collect(Collectors.toList());
        return posts.size();
    }

    public Post readFirst(UUID userId) {
        return postRepo.findFirstByUserIdOrderByCreatedTimeDesc(userId)
                .orElse(null);
    }

    public List<Post> readAllFirstPost(List<UUID> userIds){
        return userIds.stream()
                .map(this::readFirst)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<CommunityReadDto> readsTheLatestPost(){
        List<User> users = userRepo.findByPublicCheckTrue();

        List<Post> posts = readAllFirstPost(
                users.stream()
                        .map(User::getId)
                        .toList()
        );

        return posts.stream()
                .map(CommunityReadDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateById(Long postId, PostUpdateDTO postUpdateDTO){
        Post post = postRepo.findById(postId).orElseThrow();

        if(Optional.ofNullable(postUpdateDTO.getPictureUrl()).orElse("").isEmpty()){
            post.updateContent(postUpdateDTO);
        }
        else { post.updateAll(postUpdateDTO); }

        postRepo.save(post);
    }

    //delete member by id
    @Transactional
    public void deleteById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        User user = post.getUser();

        Question question = questionRepo.findByQuestionText(post.getPostTitle());
        Long questionId = question.getId();

        userQuestionService.answerQuestion(user.getId(), questionId, false);
        log.info("📍: change the answerQuestion");
        postRepo.deleteById(postId);
    }
}
