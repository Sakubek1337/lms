package com.iau.lms.controller;

import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.iau.lms.service.impl.ReportServiceImpl;
import com.iau.lms.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportServiceImpl reportService;
    private final BookServiceImpl bookService;
    private final LoanServiceImpl loanService;
    private final StudentServiceImpl studentService;

    @GetMapping("")
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("reports");
        mav.addObject("bookStats", bookService.getBookStats());
        mav.addObject("loanStats", loanService.getLoanStats());
        mav.addObject("studentStats", studentService.getStudentStats());
        return mav;
    }
}
