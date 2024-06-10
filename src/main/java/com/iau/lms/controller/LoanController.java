package com.iau.lms.controller;

import com.iau.lms.mapper.BookMapper;
import com.iau.lms.models.Options;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.request.BooksResponse;
import com.iau.lms.models.request.CreateLoanRequest;
import com.iau.lms.models.request.UpdateLoanRequest;
import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.iau.lms.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

    private final LoanServiceImpl loanService;
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


}
