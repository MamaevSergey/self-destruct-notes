package com.example.selfdestructnote.service;

import com.example.selfdestructnote.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteCleanupService {

    private final NoteRepository noteRepository;

    @Scheduled(fixedRate = 20000)
    @Transactional
    public void cleanupExpiredNotes() {
        noteRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Scheduler worked: очистил устаревшие записи за " + LocalDateTime.now());
    }
}
