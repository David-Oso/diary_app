package com.diary.DiaryApp.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequest {
    @NotNull(message = "field user name cannot be null")
    @NotEmpty(message = "field user name cannot be empty")
    @NotBlank(message = "field user name cannot be blank")
    private String userName;

    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    private String password;
}
