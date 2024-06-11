package com.iau.lms.controller;

import com.iau.lms.mapper.BookMapper;
import com.iau.lms.models.Options;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.request.BooksResponse;
import com.iau.lms.models.request.CreateLoanRequest;
import com.iau.lms.models.request.UpdateLoanRequest;
import com.iau.lms.repository.LoanRepository;
import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.iau.lms.service.impl.StudentServiceImpl;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

    private final LoanServiceImpl loanService;
    private final LoanRepository loanRepository;
    private final StudentServiceImpl studentService;
    private final BookServiceImpl bookService;

    @GetMapping({"", "/"})
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("loans/loans");
        mav.addObject("loans_response", loanService.getLoans());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getLoanById(@PathVariable("id") Long loanId) throws Exception {
        ModelAndView mav = new ModelAndView("loans/loans-page");
        mav.addObject("loanResponse", loanService.getLoanById(loanId));
        return mav;
    }

    @GetMapping("/insert")
    public ModelAndView insertLoan(){
        ModelAndView mav = new ModelAndView("loans/loans-add-form");
        mav.addObject("new_loan", new CreateLoanRequest());
        mav.addObject("options", new Options(studentService.getStudentsD(),
                BookMapper.entityListToDtoList(bookService.getBooks().getBooks())));
        return mav;
    }

    @PostMapping("/insert")
    public String insertLoan(@ModelAttribute("new_loan") CreateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        SimpleResponse response = loanService.insertLoan(request, redirectAttributes);
        if (response.getSuccess()){
            return "redirect:/loans";
        }
        return "redirect:/loans/insert";
    }

    @GetMapping("/update")
    public ModelAndView updateLoan(){
        ModelAndView mav = new ModelAndView("loans/loans-update-form");
        mav.addObject("new_loan", new UpdateLoanRequest());
        return mav;
    }

    @PostMapping("/update")
    public String updateLoan(@ModelAttribute("new_loan") UpdateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        SimpleResponse response = loanService.updateLoan(request, redirectAttributes);
        if (response.getSuccess()){
            return "redirect:/loans";
        }
        return "redirect:/loans/insert";
    }

    @GetMapping("/return/{id}")
    public String returnLoan(@RequestParam(name = "student-id", required = false) Long studentId,
                             @RequestParam(name = "book-id", required = false) Long bookId,
                             @PathVariable("id") Long loanId) throws Exception {
        loanService.returnLoan(loanId);
        if (studentId == null && bookId == null) {
            return "redirect:/loans";
        }
        if (studentId == null) {
            return "redirect:/books/" + bookId;
        }
        return "redirect:/students/" + studentId;
    }

    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadCsv() {

        StreamingResponseBody stream = outputStream -> {
            List<Loan> books = loanRepository.findAll();
            try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                try {
                    new StatefulBeanToCsvBuilder<Loan>(writer)
                            .build().write(books);
                } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "loans.csv"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(stream);
    }

    @GetMapping("/lose/{id}")
    public String loseLoan(@RequestParam(name = "student-id", required = false) Long studentId,
                             @RequestParam(name = "book-id", required = false) Long bookId,
                             @PathVariable("id") Long loanId) throws Exception {
        loanService.loseLoan(loanId);
        if (studentId == null && bookId == null) {
            return "redirect:/loans";
        }
        if (studentId == null) {
            return "redirect:/books/" + bookId;
        }
        return "redirect:/students/" + studentId;
    }

    @GetMapping("/neutralize/{id}")
    public String nLoan(@RequestParam(name = "student-id", required = false) Long studentId,
                           @RequestParam(name = "book-id", required = false) Long bookId,
                           @PathVariable("id") Long loanId) throws Exception {
        loanService.neutralizeLoan(loanId);
        if (studentId == null && bookId == null) {
            return "redirect:/loans";
        }
        if (studentId == null) {
            return "redirect:/books/" + bookId;
        }
        return "redirect:/students/" + studentId;
    }
}
