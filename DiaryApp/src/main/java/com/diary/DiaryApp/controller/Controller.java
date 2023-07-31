package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diary/user")
@AllArgsConstructor
public class Controller {
    private final UserService userService;

}
