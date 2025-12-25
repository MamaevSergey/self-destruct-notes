package com.example.selfdestructnote.service;

import com.example.selfdestructnote.exception.NoteNotFoundException;
import com.example.selfdestructnote.model.Note;
import com.example.selfdestructnote.repository.NoteRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Note getNoteById(String id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Записка не найдена или уже была прочитана!"));

        String decryptedContent = encryptionService.decrypt(note.getContent());
        note.setContent(decryptedContent);
        noteRepository.delete(note);

        return note;
    }
}
