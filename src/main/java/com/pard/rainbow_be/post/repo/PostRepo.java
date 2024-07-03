package com.pard.rainbow_be.post.repo;

import com.pard.rainbow_be.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
//    List<Post> findByPid(Integer pid); // find by category (this could be fixed)
//
//    void deleteByPid(Long pid);
}
