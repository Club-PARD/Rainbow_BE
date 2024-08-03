package com.pard.rainbow_be.user.service;

import com.pard.rainbow_be.comment.repo.CommentRepo;
import com.pard.rainbow_be.post.repo.PostRepo;
import com.pard.rainbow_be.question.entity.Question;
import com.pard.rainbow_be.question.repo.QuestionRepo;
import com.pard.rainbow_be.user.dto.UserCreateDto;
import com.pard.rainbow_be.user.dto.UserReadDto;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import com.pard.rainbow_be.userToQuestion.entity.UserQuestion;
import com.pard.rainbow_be.userToQuestion.repo.UserQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final QuestionRepo questionRepo;
    private final UserQuestionRepository userQuestionRepository;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;

    @Transactional
    public void createUser(UserCreateDto dto) {
        List<Question> questions = questionRepo.findAll();
        User user = userRepo.save(User.localToEntity(dto));

        for (Question question : questions) {
            UserQuestion userQuestion = UserQuestion.builder()
                    .user(user)
                    .question(question)
                    .build();
            userQuestionRepository.save(userQuestion);
        }
    }

    @Transactional
    public User createOrUpdateUser(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            log.info("\uD83D\uDCCD user already is made up");
            return optionalUser.get();
        } else {
            User user = User.builder()
                    .email(email)
                    .build();
            log.info("\uD83D\uDCCD user make up first");
            List<Question> questions = questionRepo.findAll();
            for (Question question : questions) {
                UserQuestion userQuestion = UserQuestion.builder()
                        .user(user)
                        .question(question)
                        .build();
                userQuestionRepository.save(userQuestion);
            }
            return userRepo.save(user);
        }
    }

    public UUID readByEmail(String email){
        User user = userRepo.findByEmail(email).orElseThrow();
        return user.getId();
    }

    public String readPetNameByID(UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        return user.getPetName();
    }

    public UserReadDto readById(UUID userId){
        User user = userRepo.findById(userId).orElseThrow();
        return new UserReadDto(user);
    }

    @Transactional
    public void updateUser(UUID id, String name, String petName) {
        User user = userRepo.findById(id).orElseThrow();
        user.localToUpdate(name, petName);
        userRepo.save(user);
    }

    @Transactional
    public boolean updatePublic(UUID userId, boolean publicCheck){
        User user = userRepo.findById(userId).orElseThrow();
        user.updateBoolean(publicCheck);
        return userRepo.save(user).getPublicCheck();
    }

    @Transactional
    public void deleteUser(UUID id){
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            postRepo.deleteByUserId(id);
            userQuestionRepository.deleteByUserId(id);
            commentRepo.deleteByOwnerId(id);
            userRepo.delete(user);
        }
    }

    public boolean validateUser(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return (user.getPassword().equals(password));
    }

    public boolean userExists(String email) {
        return userRepo.existsByEmail(email);
    }
}
