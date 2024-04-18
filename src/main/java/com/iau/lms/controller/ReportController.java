package com.iau.lms.controller;

import com.iau.lms.service.impl.ReportServiceImpl;
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

    @GetMapping("")
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("report");
        return mav;
    }
}
