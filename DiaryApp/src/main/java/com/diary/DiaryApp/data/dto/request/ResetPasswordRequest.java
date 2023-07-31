package com.diary.DiaryApp.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.diary.DiaryApp.utilities.ValidationUtils.PASSWORD_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordRequest {
    @NotNull(message = "field otp cannot be null")
    private String otp;

    @NotNull(message = "field new password cannot be null")
    @NotEmpty(message = "field new password cannot be empty")
    @NotBlank(message = "field new password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String newPassword;

    @NotNull(message = "field confirm password cannot be null")
    @NotEmpty(message = "field confirm password cannot be empty")
    @NotBlank(message = "field confirm password cannot be blank")
    private String confirmPassword;
}
