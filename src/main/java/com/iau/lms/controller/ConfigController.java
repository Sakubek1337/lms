package com.iau.lms.controller;

import com.iau.lms.service.impl.ConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/config")
public class ConfigController {

    private final ConfigServiceImpl configService;

    @GetMapping("")
    public ModelAndView getMain(){
        ModelAndView mav = new ModelAndView("configuration");
        return mav;
    }
}
