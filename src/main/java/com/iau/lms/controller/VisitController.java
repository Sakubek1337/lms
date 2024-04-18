package com.iau.lms.controller;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.models.request.VisitRequest;
import com.iau.lms.service.impl.VisitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitServiceImpl visitService;

    @GetMapping("")
    public ModelAndView getVisits(){
        ModelAndView mav = new ModelAndView("visits");
        mav.addObject("visits", visitService.getVisits());
        return mav;
    }

    @PostMapping("/insert")
    public String insertBook(@ModelAttribute VisitRequest request) throws Exception {
        visitService.recordVisit(request);
        return "redirect:/visits";
    }

    @GetMapping("/insert")
    public ModelAndView insertForm(){
        ModelAndView mav = new ModelAndView("visits-add-form");
        mav.addObject("new_visit", new VisitRequest());
        return mav;
    }
}
