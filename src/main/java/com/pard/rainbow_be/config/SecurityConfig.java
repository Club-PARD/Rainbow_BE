package com.pard.rainbow_be.config;

import com.pard.rainbow_be.oauth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.pard.rainbow_be.oauth.PrincipalOauth2UserService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS 설정 비활성화
        http.cors(AbstractHttpConfigurer::disable);

        // 모든 요청 허용
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
        );

        // 폼 로그인 설정
        http.formLogin(formLogin -> formLogin
                .loginPage("/loginForm")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .permitAll());

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll());

        // OAuth2 로그인 설정
        http.oauth2Login(
                oauth -> oauth
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/home")
                        .userInfoEndpoint(
                                userInfo ->
                                        userInfo.userService(principalOauth2UserService)
                        )
        );

        // 사용자 세부 정보 서비스 설정
        http.userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
