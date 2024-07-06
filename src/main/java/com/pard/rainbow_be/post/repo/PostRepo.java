package com.pard.rainbow_be.post.repo;

import com.pard.rainbow_be.post.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, Long> {
    //   JPA 가 적절한 query 생성해줌 (Spring Data JPA, Query Creation)
    //    List<Post> findById(Integer pid); // find by _______

    //    void deleteByPid(Long pid);

    List<Post> findAllByUserId(UUID userId);
    List<Post> findAllByUserId(UUID userId, Sort sort);
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdTime DESC limit 1")
    Optional<Post> findFirstByUserIdOrderByCreatedTimeDesc(@Param("userId") UUID userId);

}
