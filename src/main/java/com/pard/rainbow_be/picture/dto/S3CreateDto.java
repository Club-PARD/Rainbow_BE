package com.pard.rainbow_be.picture.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3CreateDto {
    private String fileName;
    private String uploadImageUrl;
}
