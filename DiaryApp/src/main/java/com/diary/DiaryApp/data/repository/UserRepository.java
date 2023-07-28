package com.diary.DiaryApp.data.repository;

import com.diary.DiaryApp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByUserName(String userName);
}
