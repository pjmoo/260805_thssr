package org.example.thssr.controller;

import lombok.RequiredArgsConstructor;
import org.example.thssr.dto.BookFormDTO;
import org.example.thssr.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public String page(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "index";
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return List.of("소설", "비문학", "문제집");
    }

    @GetMapping("/new")
    // import org.springframework.ui.Model;
    public String newBook(Model model) {
        model.addAttribute("bookForm", BookFormDTO.builder().build());
        return "form";
    }

    @PostMapping
    public String createBook(
            @ModelAttribute("bookForm") BookFormDTO bookFormDTO) {
        bookService.createBook(bookFormDTO.toEntity());
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("bookData", bookService.findById(id));
        return "detail";
    }
}
