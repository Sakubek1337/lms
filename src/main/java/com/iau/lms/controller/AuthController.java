package com.iau.lms.controller;

import com.iau.lms.models.dto.AuthStatus;
import com.iau.lms.models.dto.UserDto;
import com.iau.lms.service.impl.BookServiceImpl;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.iau.lms.service.impl.StudentServiceImpl;
import com.iau.lms.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final BookServiceImpl bookService;
    private final LoanServiceImpl loanService;
    private final StudentServiceImpl studentService;

    @GetMapping("/login")
    public ModelAndView getAuth(){
        ModelAndView mav = new ModelAndView("login");
        AuthStatus authStatus = new AuthStatus(false, false);
        mav.addObject("auth_status", authStatus);
        mav.addObject("new_user", new UserDto());
        mav.addObject("user", new UserDto());
        return mav;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(name = "user") UserDto user, HttpServletRequest request) throws Exception {
        return userService.register(user, request) ? "redirect:/dashboard" : "redirect:/login";
    }

    @GetMapping({"/", "/dashboard"})
    public ModelAndView getMain() throws Exception {
        ModelAndView mav = new ModelAndView("dashboard");
//        UserDto user = userService.getCurrentUser();
//        mav.addObject("user", user);
        mav.addObject("books_response", bookService.getBooks());
        mav.addObject("restocks", bookService.getRestocks());
        mav.addObject("loanStats", loanService.getLoanStats());
        mav.addObject("studentStats", studentService.getStudentStats());
        return mav;
    }
}
