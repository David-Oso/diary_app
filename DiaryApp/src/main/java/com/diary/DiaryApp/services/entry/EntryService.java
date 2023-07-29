package com.diary.DiaryApp.services.entry;

import com.diary.DiaryApp.data.dto.request.CreateEntryRequest;
import com.diary.DiaryApp.data.dto.request.UpdateEntryRequest;
import com.diary.DiaryApp.data.dto.response.CreateEntryResponse;
import com.diary.DiaryApp.data.dto.response.UpdateEntryResponse;
import com.diary.DiaryApp.data.model.Entry;
import org.springframework.data.domain.Page;

public interface EntryService {
    CreateEntryResponse createEntry(CreateEntryRequest createEntryRequest);
    Entry getEntryById(Long entryId);
    Entry getEntryByTitle(String title);
    Page<Entry> getAllEntries();
    UpdateEntryResponse updateEntry(UpdateEntryRequest updateentryRequest);
}
