package com.diary.DiaryApp.config.security.services;

import com.diary.DiaryApp.config.security.SecuredUser;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findUserByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("App user with this email not found."));
        return new SecuredUser(user);
    }
}
