package com.diary.DiaryApp.data.dto.request;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private Long userId;
    private String newUserName;
    private String newPassword;
}
