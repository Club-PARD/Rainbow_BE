package com.pard.rainbow_be.picture.dto;

import lombok.Getter;
import lombok.Setter;

public class S3Dto {
    @Getter
    @Setter
    public static class create{
        private String fileName;
        private String uploadImageUrl;
    }
}
