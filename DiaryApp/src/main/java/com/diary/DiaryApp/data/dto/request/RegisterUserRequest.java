package com.diary.DiaryApp.data.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.diary.DiaryApp.utilities.ValidationUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
    @NotNull(message = "field user name cannot be null")
    @NotBlank(message = "field user name cannot be blank")
    @NotEmpty(message = "field user name cannot be empty")
    @Pattern(regexp = NAME_REGEX, message = "user name must be letter starting with capital letter")
    private String userName;

    @NotNull(message = "field email cannot be null")
    @NotBlank(message = "field email cannot be blank")
    @NotEmpty(message = "field email cannot be empty")
    @Email(regexp = EMAIL_REGEX, message = "enter a valid email address")
    private String email;

    @NotNull(message = "field password cannot be null")
    @NotBlank(message = "field password cannot be blank")
    @NotEmpty(message = "field password cannot be empty")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String password;

}
