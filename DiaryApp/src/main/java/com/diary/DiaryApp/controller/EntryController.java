package com.diary.DiaryApp.controller;

import com.diary.DiaryApp.data.dto.request.CreateEntryRequest;
import com.diary.DiaryApp.data.dto.request.UpdateEntryRequest;
import com.diary.DiaryApp.data.dto.response.CreateEntryResponse;
import com.diary.DiaryApp.data.dto.response.UpdateEntryResponse;
import com.diary.DiaryApp.data.model.Entry;
import com.diary.DiaryApp.services.entry.EntryService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary/entry/")
@AllArgsConstructor
//@Hidden
public class EntryController {
    private final EntryService entryService;

    @PostMapping("create")
    public ResponseEntity<?> createEntry(@Valid @RequestBody CreateEntryRequest createEntryRequest){
        CreateEntryResponse createEntryResponse = entryService.createEntry(createEntryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createEntryResponse);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getEntryById(@Valid @PathVariable Long id){
        Entry entry = entryService.getEntryById(id);
        return ResponseEntity.ok(entry);
    }

    @GetMapping("get/title")
    public ResponseEntity<?> getEntryByTitle(@Valid @RequestParam String title){
        Entry entry = entryService.getEntryByTitle(title);
        return ResponseEntity.ok(entry);
    }

    @GetMapping("get/all/{number}")
    public ResponseEntity<?> getAllEntries(@Valid @PathVariable int number){
        Page<Entry> entries = entryService.getAllEntries(number);
        return ResponseEntity.ok(entries);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEntry(@Valid @RequestBody UpdateEntryRequest updateEntryRequest){
        UpdateEntryResponse response = entryService.updateEntry(updateEntryRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEntryByUserIdAndEntryId(@Valid @RequestParam Long userId, @RequestParam Long entryId){
        String response = entryService.deleteEntryByUserIdAndEntryId(userId, entryId);
        return  ResponseEntity.ok(response);
    }

    @DeleteMapping("delete/all/{id}")
    public  ResponseEntity<?> deleteAllByUserId(@Valid @PathVariable Long id){
        String response = entryService.deleteAllByUserId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("number-of-entries/{id}")
    public ResponseEntity<?> getNumberOfEntriesByUserId(@Valid @PathVariable Long id){
        return ResponseEntity.ok(entryService.getNumberOfEntriesByUserId(id));
    }

    @GetMapping("all/number")
    public ResponseEntity<?> getTotalNumberOfEntries(){
        return ResponseEntity.ok(entryService.getTotalNumberOfEntries());
    }
}
