package com.diary.DiaryApp.data.repository;

import com.diary.DiaryApp.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    Optional<Entry> findEntryByTitle(String title);
}
