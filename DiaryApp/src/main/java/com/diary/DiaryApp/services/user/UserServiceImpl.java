package com.diary.DiaryApp.services.user;

import com.diary.DiaryApp.data.dto.request.RegisterUserRequest;
import com.diary.DiaryApp.data.dto.response.RegisterUserResponse;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.UserRepository;
import com.diary.DiaryApp.exception.AlreadyExistsException;
import com.diary.DiaryApp.exception.NotFoundException;
import com.diary.DiaryApp.otp.OtpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final OtpService otpService;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.getUserName(), registerRequest.getEmail());
        return null;
    }

    private void checkIfUserAlreadyExists(String userName, String email) {
        User userFoundByUserName = userRepository.findUserByUserName(userName);
        User userFoundByEmail = userRepository.findUserByEmail(email);
        if(userFoundByUserName != null)
            throw new AlreadyExistsException("User with this email already exists");
        else if (userFoundByEmail != null)
            throw new AlreadyExistsException("User with this user name already exists");
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null)
            throw new NotFoundException("User with this email not found!");
        else return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if(user == null)
            throw new NotFoundException("User with user name not found!");
        else return user;
    }
}
