package com.diary.DiaryApp.otp;

import com.diary.DiaryApp.data.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(unique = true)
    private String otp;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime expirationTime = createdAt.plusMinutes(15L);
}
