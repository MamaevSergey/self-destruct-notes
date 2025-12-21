package com.example.selfdestructnote.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String content;
    private int lifetimeSeconds;
}
