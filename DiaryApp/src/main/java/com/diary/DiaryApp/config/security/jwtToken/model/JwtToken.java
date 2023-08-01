package com.diary.DiaryApp.config.security.jwtToken.model;

import com.diary.DiaryApp.data.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    private String refreshToken;
    private boolean isRevoked;
    private boolean isExpired;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private User user;
}
