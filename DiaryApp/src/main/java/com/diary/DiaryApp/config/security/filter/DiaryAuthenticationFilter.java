//package com.diary.DiaryApp.config.security.filter;
//
//import com.diary.DiaryApp.config.security.jwtToken.model.DiaryToken;
//import com.diary.DiaryApp.config.security.jwtToken.service.DiaryTokenService;
//import com.diary.DiaryApp.config.security.services.DiaryUserDetailsService;
//import com.diary.DiaryApp.config.security.services.JwtService;
//import com.diary.DiaryApp.config.security.user.AuthenticatedUser;
//import com.diary.DiaryApp.config.security.utils.AuthenticationToken;
//import com.diary.DiaryApp.data.model.User;
//import com.diary.DiaryApp.exception.DiaryAppException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@AllArgsConstructor
//public class DiaryAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private final AuthenticationManager authenticationManager;
//    private final DiaryTokenService diaryTokenService;
//    private final DiaryUserDetailsService diaryUserDetailsService;
//    private final ObjectMapper objectMapper;
//    private final JwtService jwtService;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) throws AuthenticationException {
//
//        try{
//            final User user = objectMapper.readValue(request.getInputStream(), User.class);
//
//            final Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            user.getUserName(),
//                            user.getPassword());
//
//            final Authentication authenticationResult =
//                    authenticationManager.authenticate(authentication);
//            if(authenticationResult != null){
//                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
//                return SecurityContextHolder.getContext().getAuthentication();
//            }
//        }catch (IOException exception){
//            throw new DiaryAppException("Authentication failed");
//        }
//        throw new DiaryAppException("Authentication failed");
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        final Map<String, Object> claims = new HashMap<>();
//        authResult.getAuthorities().forEach(
//                role -> claims.put("claim", role));
//
//        final String username = authResult.getPrincipal().toString();
//
//        final String accessToken = jwtService.generateAccessToken(claims, username);
//        final String refreshToken = jwtService.generateRefreshToken(username);
//
//        final AuthenticatedUser authenticatedUser =
//                (AuthenticatedUser) diaryUserDetailsService.loadUserByUsername(username);
//
//        final DiaryToken diaryToken = DiaryToken.builder()
//                .user(authenticatedUser.getUser())
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .isExpired(false)
//                .isExpired(false)
//                .build();
//        diaryTokenService.saveToken(diaryToken);
//
//        final AuthenticationToken authenticationToken =
//                AuthenticationToken.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                .build();
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(response.getOutputStream(), authenticationToken);
//    }
//}
