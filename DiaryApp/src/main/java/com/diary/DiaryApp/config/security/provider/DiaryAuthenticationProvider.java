package com.diary.DiaryApp.config.security.provider;

import com.diary.DiaryApp.config.security.services.DiaryUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DiaryAuthenticationProvider implements AuthenticationProvider {
    private final DiaryUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String requestName = authentication.getPrincipal().toString();
//        final String name = authentication.getName();
        log.info("\nres->{}", requestName);
//        log.info("\nres->{}", name);
        final String requestPassword = authentication.getCredentials().toString();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(requestName);
        final String username = userDetails.getUsername();
        final String password = userDetails.getPassword();
        if (passwordEncoder.matches(requestPassword, password)){
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Incorrect username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
