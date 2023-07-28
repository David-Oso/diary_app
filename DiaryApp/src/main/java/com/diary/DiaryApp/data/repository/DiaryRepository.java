package com.diary.DiaryApp.data.repository;

import com.diary.DiaryApp.data.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
