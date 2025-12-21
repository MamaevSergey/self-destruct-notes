package com.example.selfdestructnote.controller;

import com.example.selfdestructnote.dto.NoteRequest;
import com.example.selfdestructnote.model.Note;
import com.example.selfdestructnote.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Записки", description = "Методы для создания и чтения секретных заметок")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Создать секретную заметку", description = "Возвращает объект с ID, который является ссылкой")
    @PostMapping
    public Note createNote(@RequestBody NoteRequest request) {
        // По умолчанию ставим время жизни 3600 сек (1 час), если не передали
        int lifetime = request.getLifetimeSeconds() > 0 ? request.getLifetimeSeconds() : 3600;
        return noteService.createNote(request.getContent(), lifetime);
    }

    @Operation(summary = "Прочитать заметку", description = "Внимание! После прочтения заметка удаляется.")
    @GetMapping("/{id}")
    public Note getNote(@PathVariable String id) {
        return noteService.getNoteById(id);
    }
}
