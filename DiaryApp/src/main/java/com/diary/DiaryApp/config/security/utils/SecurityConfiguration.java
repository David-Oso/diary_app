package com.diary.DiaryApp.config.security.utils;

import com.diary.DiaryApp.config.security.filter.DiaryAuthenticationFilter;
import com.diary.DiaryApp.config.security.filter.DiaryAuthorizationFilter;
import com.diary.DiaryApp.config.security.jwtToken.service.DiaryTokenService;
import com.diary.DiaryApp.config.security.services.DiaryUserDetailsService;
import com.diary.DiaryApp.config.security.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationManager authenticationManager;
    private final DiaryUserDetailsService diaryUserDetailsService;
    private final DiaryTokenService diaryTokenService;
    private ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sessionManagement ->
                        sessionManagement.
                                sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(
                                HttpMethod.POST,
                                WhiteList.freeAccess())
                                .permitAll()
                                .requestMatchers(WhiteList.swagger())
                                .permitAll()
                                .anyRequest().authenticated())
                .addFilterAt(
                        login(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        new DiaryAuthorizationFilter(
                                diaryUserDetailsService,
                                diaryTokenService,
                                jwtService
                        ),
                        DiaryAuthenticationFilter.class
                )
                .build();

    }

    private UsernamePasswordAuthenticationFilter login() {
        final UsernamePasswordAuthenticationFilter authenticationFilter =
                new DiaryAuthenticationFilter(
                        authenticationManager,
                        diaryTokenService,
                        diaryUserDetailsService,
                        objectMapper,
                        jwtService);
        authenticationFilter.setFilterProcessesUrl("/api/v1/diary/auth/login");
        return authenticationFilter;
    }
}
