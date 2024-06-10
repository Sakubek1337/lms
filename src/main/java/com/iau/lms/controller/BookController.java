package com.iau.lms.controller;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.models.request.RestockRequest;
import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;
    private final LoanServiceImpl loanService;

    @GetMapping({"", "/"})
    public ModelAndView getBooks(){
        ModelAndView mav = new ModelAndView("books/books");
        mav.addObject("books_response", bookService.getBooks());
        mav.addObject("new_book", new Book());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getStudentById(@PathVariable("id") Long bookId) throws Exception {
        ModelAndView mav = new ModelAndView("books/books-page");
        mav.addObject("bookResponse", bookService.getBookById(bookId));
        mav.addObject("loansResponse", loanService.getLoansByBookId(bookId));
        return mav;
    }

    @PostMapping("/insert")
    public String insertBook(@ModelAttribute InsertBookRequest request){
        bookService.insertBook(request);
        return "redirect:/books";
    }

    @GetMapping("/insert")
    public ModelAndView insertForm(){
        ModelAndView mav = new ModelAndView("books/books-add-form");
        mav.addObject("new_book", new InsertBookRequest());
        return mav;
    }

    @GetMapping("/update/{bookId}")
    public ModelAndView updateBook(@PathVariable("bookId") Long bookId) throws Exception {
        ModelAndView mav = new ModelAndView("books/books-update-form");
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

    @PostMapping("/restock/{bookId}")
    public String restockBookById(@PathVariable("bookId") Long bookId, @ModelAttribute("restock") Integer num) throws Exception {
        bookService.restockBookById(bookId, num);
        return "redirect:/books";
    }

    @GetMapping("/delete/{bookId}")
    public ModelAndView deleteBook(@PathVariable("bookId") Long bookId) throws Exception {
        ModelAndView mav = new ModelAndView("books/books-delete-form");
        mav.addObject("book", bookService.getBookEntityById(bookId));
        return mav;
    }
}
