package com.iau.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("")
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("profile");
        return mav;
    }
}
