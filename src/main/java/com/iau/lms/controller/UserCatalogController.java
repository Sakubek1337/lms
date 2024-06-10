package com.iau.lms.controller;

import com.iau.lms.service.impl.BookServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog")
@RolesAllowed("USER")
public class UserCatalogController {

    private final BookServiceImpl bookService;

    @GetMapping(value = {"", "/"})
    public ModelAndView mainPage(){
        ModelAndView mav = new ModelAndView("catalog");
        mav.addObject("books", bookService.getBooks());
        return mav;
    }
}
