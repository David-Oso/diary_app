package com.diary.DiaryApp.config.security.services;

import com.diary.DiaryApp.config.security.user.AuthenticatedUser;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiaryUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userService.getUserByEmail(email);
        return new AuthenticatedUser(user);
    }
}
