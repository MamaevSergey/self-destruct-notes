package com.example.selfdestructnote.controller;

import com.example.selfdestructnote.model.Note;
import com.example.selfdestructnote.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Важно: просто Controller, не RestController!
@RequiredArgsConstructor
public class ViewController {

    private final NoteService noteService;

    // Главная страница (форма создания)
    @GetMapping("/")
    public String index() {
        return "index"; // Spring будет искать файл index.html в папке templates
    }

    // Обработка формы создания
    @PostMapping("/create")
    public String createNote(@RequestParam String content,
                             @RequestParam int lifetime,
                             Model model) {
        Note note = noteService.createNote(content, lifetime);

        // Передаем данные на страницу
        model.addAttribute("message", "Записка создана!");
        // Формируем ссылку (в реальном проекте домен нужно брать из конфига)
        model.addAttribute("link", "http://localhost:8080/view/" + note.getId());

        return "result"; // Возвращаем страницу result.html
    }

    // Страница просмотра записки
    @GetMapping("/view/{id}")
    public String viewNote(@PathVariable String id, Model model) {
        try {
            Note note = noteService.getNoteById(id);
            model.addAttribute("noteContent", note.getContent());
            return "view"; // Страница с текстом записки
        } catch (Exception e) {
            model.addAttribute("error", "Записка не найдена или уже уничтожена.");
            return "view";
        }
    }

    @GetMapping("/faq")
    public String viewFAQ() {
        return "faq"; // Возвращаем view_faq.html
    }

    @GetMapping("/privacypolicy")
    public String viewPrivacyPolicy() {
        return "privacy"; // Возвращаем privacy_policy.html
    }
}
