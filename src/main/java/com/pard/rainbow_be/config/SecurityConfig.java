package com.pard.rainbow_be.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${sincerely.server.domain}")
    private String domain;

    @Value("${sincerely.client.domain1}")
    private String domain1;

    @Value("${sincerely.client.domain2}")
    private String domain2;

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(au -> au
                        .requestMatchers("/api/auth/**","/api/**").permitAll()
//                        .anyRequest().permitAll())
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가


        return http.build();
    }

    // 위에꺼 지우고, 아래꺼 사용 // 보안상 matcher되는 부븐 달라진당

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(au -> au
//                        .requestMatchers("/api/auth/**","/api/**").permitAll()
//                        .anyRequest().authenticated())
////               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
//
//        return http.build();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin(domain);
        configuration.addAllowedOrigin(domain1);
        configuration.addAllowedOrigin(domain2);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("DELETE");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); //preflight 결과를 1시간동안 캐시에 저장

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
