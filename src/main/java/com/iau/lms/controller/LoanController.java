package com.iau.lms.controller;

import com.iau.lms.service.impl.LoanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/loans")
public class LoanController {

    private final LoanServiceImpl loanService;

    @GetMapping("")
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("loans");
        return mav;
    }
}
