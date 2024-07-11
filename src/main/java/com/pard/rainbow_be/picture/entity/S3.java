package com.pard.rainbow_be.picture.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class S3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="file_name", nullable = false, columnDefinition = "TEXT")
    private String fileName;

    @Lob
    @Column(name = "upload_image_url", nullable = false, columnDefinition = "TEXT")
    private String uploadImageUrl;



}
