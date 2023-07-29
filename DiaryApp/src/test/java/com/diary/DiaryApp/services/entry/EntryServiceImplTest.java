package com.diary.DiaryApp.services.entry;

import com.diary.DiaryApp.data.dto.request.CreateEntryRequest;
import com.diary.DiaryApp.data.dto.request.UpdateEntryRequest;
import com.diary.DiaryApp.data.dto.response.CreateEntryResponse;
import com.diary.DiaryApp.data.dto.response.UpdateEntryResponse;
import com.diary.DiaryApp.data.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class EntryServiceImplTest {
    @Autowired EntryService entryService;
    private CreateEntryRequest createEntryRequest1;
    private CreateEntryRequest createEntryRequest2;
    private UpdateEntryRequest updateEntryRequest;

    @BeforeEach
    void setUp() {
        createEntryRequest1 = new CreateEntryRequest();
        createEntryRequest1.setUserId(1L);
        createEntryRequest1.setTitle("My first diary entry");
        createEntryRequest1.setBody("This is my first entry in my_diary app");

        createEntryRequest2 = new CreateEntryRequest();
        createEntryRequest2.setUserId(1L);
        createEntryRequest2.setTitle("My second diary entry");
        createEntryRequest2.setBody("This is my second entry in my_diary app");

        updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setUserId(1L);
        updateEntryRequest.setEntryId(2L);
        updateEntryRequest.setTitle("Updating the second entry");
        updateEntryRequest.setBody("I am currently updating the second entry");
    }

    @Test
    void createEntry() {
        CreateEntryResponse createEntryResponse1 = entryService.createEntry(createEntryRequest1);
        assertThat(createEntryResponse1.getMessage()).isEqualTo("New Entry Added");
        assertThat(createEntryResponse1.isCreated()).isEqualTo(true);

        CreateEntryResponse createEntryResponse2 = entryService.createEntry(createEntryRequest2);
        assertThat(createEntryResponse2.getMessage()).isEqualTo("New Entry Added");
        assertThat(createEntryResponse2.isCreated()).isEqualTo(true);
    }

    @Test
    void getEntryBIdTest(){
        Entry entry = entryService.getEntryById(1L);
        assertThat(entry.getTitle()).isEqualTo(createEntryRequest1.getTitle());
        assertThat(entry.getBody()).isEqualTo(createEntryRequest1.getBody());
    }
    @Test
    void getEntryByTitleTest(){
        Entry entry = entryService.getEntryByTitle("My first diary entry");
        assertThat(entry.getBody()).isEqualTo(createEntryRequest1.getBody());
    }
    @Test
    void updateEntryTest(){
        UpdateEntryResponse updateEntryResponse = entryService.updateEntry(updateEntryRequest);
        assertThat(updateEntryResponse.getMessage()).isEqualTo("Entry updated");
        assertThat(updateEntryResponse.isUpdated()).isEqualTo(true);
    }
}