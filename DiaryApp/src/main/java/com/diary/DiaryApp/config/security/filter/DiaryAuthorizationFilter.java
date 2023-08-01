package com.diary.DiaryApp.config.security.filter;

import com.diary.DiaryApp.config.security.jwtToken.service.JwtTokenService;
import com.diary.DiaryApp.config.security.services.DiaryUserDetailsService;
import com.diary.DiaryApp.config.security.services.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@AllArgsConstructor
public class DiaryAuthorizationFilter extends OncePerRequestFilter {
    private final DiaryUserDetailsService diaryUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String bearer = "Bearer ";
        if(StringUtils.hasText(authHeader) &&
                StringUtils.startsWithIgnoreCase(authHeader, bearer)){
            final String accessToken = authHeader.substring(bearer.length());
            if (jwtService.isValid(accessToken) &&
                    jwtTokenService.isTokenValid(accessToken)){
                final String email = jwtService.extractUserNameFromToken(accessToken);
                if(email != null){
                    UserDetails userDetails =
                            diaryUserDetailsService.loadUserByUsername(email);
                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
