package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.services.entry.EntryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diary/entry")
@AllArgsConstructor
public class EntryController {
    private final EntryService entryService;
}

