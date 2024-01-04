package com.diary.DiaryApp.config.security.jwtToken;

import com.diary.DiaryApp.data.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiaryJwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, columnDefinition = "Text")
    private String accessToken;
    @Column(unique = true, columnDefinition = "Text")
    private String refreshToken;
    private boolean isExpired;
    private boolean isRevoked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
