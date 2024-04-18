package com.iau.lms.controller;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping("")
    public ModelAndView getBooks(){
        ModelAndView mav = new ModelAndView("books");
        mav.addObject("books_response", bookService.getBooks());
        mav.addObject("new_book", new Book());
        return mav;
    }

    @PostMapping("/insert")
    public String insertBook(@ModelAttribute InsertBookRequest request){
        bookService.insertBook(request);
        return "redirect:/books";
    }

    @GetMapping("/insert")
    public ModelAndView insertForm(){
        ModelAndView mav = new ModelAndView("books-add-form");
        mav.addObject("new_book", new InsertBookRequest());
        return mav;
    }

    @GetMapping("/update/{bookId}")
    public ModelAndView updateBook(@PathVariable("bookId") Long bookId) throws Exception {
        ModelAndView mav = new ModelAndView("books-update-form");
        mav.addObject("book", bookService.getBookEntityById(bookId));
        return mav;
    }

    @PostMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Long bookId, @ModelAttribute("book") Book book){
        bookService.updateBook(bookId, book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{bookId}")
    public String deleteBook1(@PathVariable("bookId") Long bookId){
        bookService.deleteBookbyId(bookId);
        return "redirect:/books";
    }

    @GetMapping("/delete/{bookId}")
    public ModelAndView deleteBook(@PathVariable("bookId") Long bookId) throws Exception {
        ModelAndView mav = new ModelAndView("books-delete-form");
        mav.addObject("book", bookService.getBookEntityById(bookId));
        return mav;
    }
}
