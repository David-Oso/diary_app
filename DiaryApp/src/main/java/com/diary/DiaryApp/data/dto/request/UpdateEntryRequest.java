package com.diary.DiaryApp.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEntryRequest {
    private Long userId;
    private Long entryId;
    private String title;
    private String body;
}
