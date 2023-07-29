package com.diary.DiaryApp.services.entry;

import com.diary.DiaryApp.data.dto.request.CreateEntryRequest;
import com.diary.DiaryApp.data.dto.response.CreateEntryResponse;
import com.diary.DiaryApp.data.model.Entry;

public interface EntryService {
    CreateEntryResponse createEntry(CreateEntryRequest createEntryRequest);
    Entry getEntryById(Long entryId);
    Entry getEntryByTitle(String title);
    UpdateEntryResponse updateEntry(UpdateEntryRequest updateentryRequest);
}
