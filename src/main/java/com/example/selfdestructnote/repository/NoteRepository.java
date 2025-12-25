package com.example.selfdestructnote.repository;

import com.example.selfdestructnote.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
