package com.pard.rainbow_be.picture.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pard.rainbow_be.picture.entity.S3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class S3ReadDto {
    private Long id;
    private String fileName;
    private String uploadImageUrl;

    public S3ReadDto(S3 s3) {
        this.id = s3.getId();
        this.fileName = s3.getFileName();
        this.uploadImageUrl = s3.getUploadImageUrl();
    }
}
