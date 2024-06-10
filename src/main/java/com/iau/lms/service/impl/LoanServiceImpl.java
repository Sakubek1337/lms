package com.iau.lms.service.impl;

import com.iau.lms.mapper.LoanMapper;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.CreateLoanRequest;
import com.iau.lms.models.request.UpdateLoanRequest;
import com.iau.lms.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl {

    private final LoanRepository loanRepository;
    private final BookServiceImpl bookService;
    private final StudentServiceImpl studentService;

    public SimpleResponse getLoans(){
        return SimpleResponse.builder()
                .result(LoanMapper.entityListToDtoList(loanRepository.findAll()))
                .build();
    }

    public SimpleResponse getLoansByStudentId(Long studentId){
        return SimpleResponse.builder()
                .result(LoanMapper.entityListToDtoList(loanRepository.findByStudentId(studentId)))
                .success(true)
                .build();
    }

    public SimpleResponse getLoansByBookId(Long bookId){
        return SimpleResponse.builder()
                .result(LoanMapper.entityListToDtoList(loanRepository.findByBookId(bookId)))
                .success(true)
                .build();
    }

    public SimpleResponse getLoanById(Long loanId) throws Exception {
        if (loanRepository.existsById(loanId)){
            return SimpleResponse.builder()
                    .result(LoanMapper.entityToDto(getLoanEntityById(loanId)))
                    .success(true)
                    .message("kek")
                    .build();
        }
        return SimpleResponse.builder()
                .result(null)
                .success(false)
                .message("kek")
                .build();
    }

    public SimpleResponse insertLoan(CreateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Book book = bookService.getBookEntityById(request.getBookId());
        Student student = studentService.getStudentEntityById(request.getStudentId());
        if (loanRepository.existsByBookAndStudent(book, student)){
            redirectAttributes.addFlashAttribute("");
            return SimpleResponse.builder()
                    .success(false)
                    .build();
        }
        Loan loan = LoanMapper.createRequestToEntity(request);
        loan.setStudent(student);
        loan.setBook(book);
        loanRepository.save(loan);
        return SimpleResponse.builder()
                .success(true)
                .build();
    }

    public SimpleResponse updateLoan(UpdateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Book book = bookService.getBookEntityById(request.getBookId());
        Student student = studentService.getStudentEntityById(request.getStudentId());
        if (loanRepository.existsByBookAndStudent(book, student)){
            redirectAttributes.addFlashAttribute("");
            return SimpleResponse.builder()
                    .success(false)
                    .build();
        }
        Loan loan = LoanMapper.updateRequestToEntity(request);
        loan.setStudent(student);
        loan.setBook(book);
        loanRepository.save(loan);
        return SimpleResponse.builder()
                .success(true)
                .build();
    }

    public Loan getLoanEntityById(Long loanId) throws Exception {
        return loanRepository.findById(loanId).orElseThrow(Exception::new);
    }
}
