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
public class ResetPasswordRequest {
    @NotNull(message = "field otp cannot be null")
    private String otp;

    @NotNull(message = "field new password cannot be null")
    @NotEmpty(message = "field new password cannot be empty")
    @NotBlank(message = "field new password cannot be blank")
    private String newPassword;

    @NotNull(message = "field confirm password cannot be null")
    @NotEmpty(message = "field confirm password cannot be empty")
    @NotBlank(message = "field confirm password cannot be blank")
    private String confirmPassword;
}
