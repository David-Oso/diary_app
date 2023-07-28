package com.diary.DiaryApp.data.repository;

import com.diary.DiaryApp.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
