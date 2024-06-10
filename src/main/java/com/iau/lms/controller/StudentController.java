package com.iau.lms.controller;

import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.CreateStudentRequest;
import com.iau.lms.models.request.UpdateStudentRequest;
import com.iau.lms.service.impl.LoanServiceImpl;
import com.iau.lms.service.impl.StudentServiceImpl;
import com.iau.lms.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final LoanServiceImpl loanService;

    @GetMapping({"", "/"})
    public ModelAndView main(){
        ModelAndView mav = new ModelAndView("students/students");
        mav.addObject("listResponse", studentService.getStudents());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getStudentById(@PathVariable("id") Long studentId) throws Exception {
        ModelAndView mav = new ModelAndView("students/students-page");
        mav.addObject("studentResponse", studentService.getStudentById(studentId));
        mav.addObject("loansResponse", loanService.getLoansByStudentId(studentId));
        return mav;
    }

    @GetMapping("/insert")
    public ModelAndView insertForm(){
        ModelAndView mav = new ModelAndView("students/students-add-form");
        mav.addObject("new_student", new CreateStudentRequest());
        return mav;
    }

    @PostMapping("/insert")
    public String insertStudent(@ModelAttribute CreateStudentRequest request,
                                RedirectAttributes redirectAttributes){
        SimpleResponse response = studentService.createStudent(request, redirectAttributes);
        if (response.getSuccess()){
            return "redirect:/students";
        }
        return "redirect:/students/insert";
    }

    @GetMapping("/update/{studentId}")
    public ModelAndView updateStudent(@PathVariable("studentId") Long studentId) throws Exception {
        ModelAndView mav = new ModelAndView("students/students-update-form");
        mav.addObject("student", studentService.getUpdateRequestById(studentId));
        return mav;
    }

    @PostMapping("/update/{studentId}")
    public String updateStudent(@PathVariable("studentId") Long studentId,
                                @ModelAttribute("student") UpdateStudentRequest student,
                                RedirectAttributes redirectAttributes){
        SimpleResponse response = studentService.updateStudent(studentId, student, redirectAttributes);
        if (response.getSuccess()){
            return "redirect:/students";
        }
        return "redirect:/students/update/" + studentId;
    }

    @PostMapping("/delete/{studentId}")
    public String deleteStudent1(@PathVariable("studentId") Long studentId, RedirectAttributes redirectAttributes){
        studentService.deleteStudent(studentId);
        return "redirect:/students";
    }

    @GetMapping("/delete/{studentId}")
    public ModelAndView deleteStudent(@PathVariable("studentId") Long studentId) throws Exception {
        ModelAndView mav = new ModelAndView("students/students-delete-form");
        mav.addObject("student", studentService.getStudentEntityById(studentId));
        return mav;
    }
}
