package org.example.thssr.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.thssr.dto.BookFormDTO;
import org.example.thssr.dto.Update;
import org.example.thssr.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
//    public String page(Model model, @RequestParam(required = false) String keyword) {
    public String page(Model model, @RequestParam(defaultValue = "") String keyword) {
//        if (keyword != null) {
//            model.addAttribute("books", bookService.search(keyword));
//        } else {
//            model.addAttribute("books", bookService.findAll());
//        }
        model.addAttribute("books", bookService.search(keyword));
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

    //    @PostMapping
    @PostMapping("/new")
    public String createBook(
//            @ModelAttribute("bookForm") BookFormDTO bookFormDTO) {
            @Valid @ModelAttribute("bookForm") BookFormDTO bookFormDTO,
            BindingResult bindingResult) {
        // 전역 오류
        if (bookFormDTO.getAuthor().equals("김자바") || bookFormDTO.getTitle().equals("김자바")) {
            bindingResult.reject("author.noKimjava", "김자바는 등록할 수 없습니다");
        }
        if (bindingResult.hasErrors()) {
            return "form"; // -> 요청 객체가 유지가 되면서 이전에 입력한 값이 남아있게 됨
        }
        bookService.createBook(bookFormDTO.toEntity());
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {
        model.addAttribute("bookData", bookService.findById(id));
        return "detail";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("bookForm",
                BookFormDTO.fromEntity(bookService.findById(id)));
        model.addAttribute("bookId", id);
        return "form";
    }

    //    @PostMapping("/{id}")
    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable long id,
                             @Validated(Update.class) @ModelAttribute("bookForm") BookFormDTO bookFormDTO,
                             RedirectAttributes redirectAttributes,
                             BindingResult bindingResult,
                             Model model) {
        System.out.println("BookController.updateBook");
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookId", id);
            return "form";
        }
        bookService.updateBook(id, bookFormDTO.toEntity());
        redirectAttributes.addFlashAttribute("msg", "%d가 수정되었습니다".formatted(id));
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id,
                         RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("msg", "%d가 삭제되었습니다".formatted(id));
        return "redirect:/books";
    }
}
