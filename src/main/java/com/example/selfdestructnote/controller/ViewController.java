package com.example.selfdestructnote.controller;

import com.example.selfdestructnote.model.Note;
import com.example.selfdestructnote.service.NoteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final NoteService noteService;

    @Value("${app.base-url}")
    private String baseLink;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/create")
    public String createNote(@RequestParam String content,
                             @RequestParam int lifetime,
                             Model model) {
        Note note = noteService.createNote(content, lifetime);

        model.addAttribute("message", "Записка создана!");
        model.addAttribute("link", baseLink + "/view/" + note.getId());

        return "result";
    }

    @GetMapping("/view/{id}")
    public String viewNote(@PathVariable String id, Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("X-Robots-Tag", "noindex, nofollow");
        try {
            Note note = noteService.getNoteById(id);
            model.addAttribute("noteContent", note.getContent());
            return "view";
        } catch (Exception e) {
            model.addAttribute("error", "Записка не найдена или уже уничтожена.");
            return "404";
        }
    }

    @GetMapping("/faq")
    public String viewFAQ() {
        return "faq";
    }

    @GetMapping("/privacypolicy")
    public String viewPrivacyPolicy() {
        return "privacy";
    }
}
