package com.iau.lms.controller;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.models.request.RestockRequest;
import com.iau.lms.repository.BookRepository;
import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;
    private final BookRepository bookRepository;
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
    public String insertBook(@ModelAttribute InsertBookRequest request) throws Exception {
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
    public String updateBook(@PathVariable("bookId") Long bookId, @ModelAttribute("book") Book book) throws Exception {
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
        ModelAndView mav = new ModelAndView("books/books-delete-form");
        mav.addObject("book", bookService.getBookEntityById(bookId));
        return mav;
    }

    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadCsv() {

        StreamingResponseBody stream = outputStream -> {
            List<Book> books = bookRepository.findAll();
            try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                try {
                    new StatefulBeanToCsvBuilder<Book>(writer)
                            .build().write(books);
                } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "book_details.csv"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(stream);
    }
}
