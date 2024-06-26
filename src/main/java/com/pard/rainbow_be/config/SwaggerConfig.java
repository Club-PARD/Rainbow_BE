package com.pard.rainbow_be.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("머ㄴ가 항상 저렴한 2ㅣ십대들")
                .description(" ‘청춘은 돈이 부족해도 낭만을 좇는것’ 이라는 정의를 실현할 수 있도록 낭만의 경험을 공유하는 동기부여 서비스")
                .version("0.0.1");
    }
}