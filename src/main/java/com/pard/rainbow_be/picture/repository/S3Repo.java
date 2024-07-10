package com.pard.rainbow_be.picture.repository;

import com.pard.rainbow_be.picture.entity.S3;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repo extends JpaRepository<S3, Long> {
    void findByAndUploadImageUrl(String uploadImageUrl);
}
