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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                             RedirectAttributes redirectAttributes) {
        Note note = noteService.createNote(content, lifetime);
        redirectAttributes.addFlashAttribute("message", "Записка создана!");
        redirectAttributes.addFlashAttribute("link", baseLink + "/view/" + note.getId());

        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(Model model) {
        if (!model.containsAttribute("link")) {
            return "redirect:/";
        }
        return "result";
    }

    @GetMapping("/view/{id}")
    public String showConfirmPage(@PathVariable String id, Model model) {
        if (noteService.exists(id)) {
            model.addAttribute("id", id);
            return "confirm"; // Показываем новую страницу подтверждения
        }
        return "404";
    }

    @PostMapping("/view/{id}")
    public String viewNote(@PathVariable String id, Model model, HttpServletResponse response) {
        // Настройки кэша остаются здесь
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        try {
            Note note = noteService.getNoteById(id);
            model.addAttribute("noteContent", note.getContent());
            return "view"; // Показываем оригинальную страницу с текстом
        } catch (Exception e) {
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
