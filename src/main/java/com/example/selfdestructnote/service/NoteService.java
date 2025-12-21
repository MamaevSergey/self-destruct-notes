package com.example.selfdestructnote.service;

import com.example.selfdestructnote.exception.NoteNotFoundException;
import com.example.selfdestructnote.model.Note;
import com.example.selfdestructnote.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final EncryptionService encryptionService;

    public Note createNote(String content, int lifetimeSeconds) {
        Note note = new Note();
        note.setContent(encryptionService.encrypt(content));
        note.setCreatedAt(LocalDateTime.now());
        note.setExpiresAt(LocalDateTime.now().plusSeconds(lifetimeSeconds));

        return noteRepository.save(note);
    }

    // Метод чтения записки
    public Note getNoteById(String id) {
        // Ищем записку, если нет - выбрасываем ошибку (пока RuntimeException для простоты)
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Записка не найдена или уже была прочитана!"));

        String decryptedContent = encryptionService.decrypt(note.getContent());
        note.setContent(decryptedContent);
        noteRepository.delete(note); // Удаляем из базы навсегда!

        return note;
    }
}
