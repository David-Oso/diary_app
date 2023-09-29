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
public class CreateEntryRequest {
    @NotNull(message = "field user id cannot be null")
    private Long userId;

    @NotNull(message = "field title cannot be null")
    @NotBlank(message = "field title cannot be blank")
    @NotEmpty(message = "field title cannot be empty")
    private String title;

    @NotNull(message = "field body cannot be null")
    @NotBlank(message = "field body cannot be blank")
    @NotEmpty(message = "field body cannot be empty")
    private String body;

}
