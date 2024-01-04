package com.diary.DiaryApp.config.security.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryJwtTokenRepository extends JpaRepository<DiaryJwtToken, Integer> {
    @Query(value = """
        select t from DiaryJwtToken  t inner join User user
        on t.user.id = user.id
        where  user.id = :id and (t.isExpired = false or t.isRevoked = false)
        """)
    List<DiaryJwtToken> findAllValidTokenUser(Integer id);
    List<DiaryJwtToken> findAllByUserId(Long userId);
    Optional<DiaryJwtToken> findByAccessToken(String accessToken);
}
