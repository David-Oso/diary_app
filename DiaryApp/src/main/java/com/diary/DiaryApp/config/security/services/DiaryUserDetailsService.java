package com.diary.DiaryApp.config.security.services;

import com.diary.DiaryApp.config.security.user.AuthenticatedUser;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiaryUserDetailsService implements UserDetailsService {
//    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByUserName(username).orElseThrow(
                (()-> new UsernameNotFoundException("User with this user name not found!")));
        return new AuthenticatedUser(user);
    }
}
