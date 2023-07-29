package com.diary.DiaryApp.services.entry;

import com.diary.DiaryApp.data.dto.request.CreateEntryRequest;
import com.diary.DiaryApp.data.dto.request.UpdateEntryRequest;
import com.diary.DiaryApp.data.dto.response.CreateEntryResponse;
import com.diary.DiaryApp.data.dto.response.UpdateEntryResponse;
import com.diary.DiaryApp.data.model.Diary;
import com.diary.DiaryApp.data.model.Entry;
import com.diary.DiaryApp.data.model.User;
import com.diary.DiaryApp.data.repository.EntryRepository;
import com.diary.DiaryApp.exception.DiaryAppException;
import com.diary.DiaryApp.exception.NotFoundException;
import com.diary.DiaryApp.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{
    private final EntryRepository entryRepository;
    private final UserService userService;
    @Override
    public CreateEntryResponse createEntry(CreateEntryRequest createEntryRequest) {
        User user = userService.getUserById(createEntryRequest.getUserId());
        checkIfUserIsEnabled(user);
        Diary diary = user.getDiary();
        Entry entry = getNewEntry(createEntryRequest);
        diary.getEntries().add(entry);
        userService.saveUser(user);
        return CreateEntryResponse.builder()
                .message("New Entry Added")
                .isCreated(true)
                .build();
    }

    private void checkIfUserIsEnabled(User user) {
        if (!(user.isEnabled()))
            throw new DiaryAppException("User is not verified");
    }

    private static Entry getNewEntry(CreateEntryRequest createEntryRequest) {
        Entry entry = new Entry();
        entry.setTitle(createEntryRequest.getTitle());
        entry.setBody(createEntryRequest.getBody());
        if (createEntryRequest.getBody().length() > 50)
            entry.setDescription(
                    createEntryRequest.getBody().substring(0, 50)+"...");
        else
            entry.setDescription(createEntryRequest.getBody());
        entry.setCreatedAt(LocalDateTime.now());
        return entry;
    }

    @Override
    public Entry getEntryById(Long entryId) {
        return entryRepository.findById(entryId).orElseThrow(
                ()-> new NotFoundException("Entry not found"));
    }

    @Override
    public Entry getEntryByTitle(String title) {
        return entryRepository.findEntryByTitle(title).orElseThrow(
                ()-> new NotFoundException("Entry with title %s not found".formatted(title)));
    }

    @Override
    public UpdateEntryResponse updateEntry(UpdateEntryRequest updateentryRequest) {
        User user = userService.getUserById(updateentryRequest.getUserId());
        Diary diary = user.getDiary();
        Set<Entry> entries = diary.getEntries();
        Entry entry = getEntryFromEntries(entries, updateentryRequest.getEntryId());
        updateAnEntry(updateentryRequest, entry);
        userService.saveUser(user);
        return UpdateEntryResponse.builder()
                .message("Entry updated")
                .isUpdated(true)
                .build();
    }

    private Entry getEntryFromEntries(Set<Entry> entries, Long entryId) {
        for(Entry entry : entries){
            if(entry.getId().equals(entryId))
                return entry;
        }
        throw new NotFoundException("Entry not found");
    }

    private static void updateAnEntry(UpdateEntryRequest updateentryRequest, Entry entry) {
        entry.setTitle(updateentryRequest.getTitle());
        entry.setBody(updateentryRequest.getBody());
        if (updateentryRequest.getBody().length() > 50)
            entry.setDescription(
                    updateentryRequest.getBody().substring(0, 50)+"...");
        else
            entry.setDescription(updateentryRequest.getBody());
        entry.setUpdatedAt(LocalDateTime.now());
    }
}
