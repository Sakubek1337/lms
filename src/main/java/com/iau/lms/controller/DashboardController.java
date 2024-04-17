package com.iau.lms.controller;

import com.iau.lms.models.dto.UserDto;
import com.iau.lms.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class DashboardController {

    private final UserServiceImpl userService;

}
