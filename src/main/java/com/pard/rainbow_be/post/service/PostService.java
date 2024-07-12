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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final UserQuestionService userQuestionService;


    private Map<UUID, List<PostReadDTO>> userPostLists = new ConcurrentHashMap<>();
    private Map<UUID, AtomicInteger> userIndices = new ConcurrentHashMap<>();


    public void createPost(PostCreateDTO postCreateDTO, UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        if (postCreateDTO.getPictureUrl() == null || postCreateDTO.getPictureUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Picture URL cannot be empty or null");
        }
        else { userQuestionService.answeredQuestion(userId, postCreateDTO.getPostTitle(), true);
         }
        postRepo.save(Post.toEntity(postCreateDTO, user));
    }


    public List<PostReadDTO> readAll(UUID userId) {
        AtomicInteger index = new AtomicInteger(1);
        List<PostReadDTO> postList = postRepo.findAllByUserId(userId, Sort.by(Sort.Direction.ASC, "createdTime"))
                .stream()
                .map(post -> {
                    PostReadDTO dto = new PostReadDTO(post);
                    dto.setIndex(index.getAndIncrement());
                    return dto;
                })
                .collect(Collectors.toList());

        userPostLists.put(userId, postList);
        userIndices.put(userId, index);

        return postList;
    }

    public PostReadDTO findById(UUID userId, Long postId) {
        List<PostReadDTO> postList = userPostLists.get(userId);
        if (postList == null) {
            throw new IllegalStateException("Posts have not been initialized for user: " + userId + ". Call readAll() first.");
        }
        return postList.stream()
                .filter(dto -> dto.getPostId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
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
                .sorted(Comparator.comparing(Post::getPostId))
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
        postRepo.deleteById(postId);
    }
}
