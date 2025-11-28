package com.github.Tyrbropro.order.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Разрешаем доступ ко всем endpoint'ам без авторизации
                )
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF защиту
                .formLogin(AbstractHttpConfigurer::disable) // Отключаем форму логина
                .httpBasic(AbstractHttpConfigurer::disable) // Отключаем базовую аутентификацию
                .build();
    }
}
