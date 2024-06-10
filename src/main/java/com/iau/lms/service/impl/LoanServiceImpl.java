package com.iau.lms.service.impl;

import com.iau.lms.enums.LoanStatus;
import com.iau.lms.enums.RestockType;
import com.iau.lms.mapper.LoanMapper;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.dto.LoanStatsDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.CreateLoanRequest;
import com.iau.lms.models.request.RestockRequest;
import com.iau.lms.models.request.UpdateLoanRequest;
import com.iau.lms.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

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

    public LoanStatsDto getLoanStats(){
        return LoanStatsDto.builder()
                .total(loanRepository.count())
                .last7days(loanRepository.getTotalLoansLast7())
                .last30days(loanRepository.getTotalLoansLast30())
                .totalLost(loanRepository.getTotalLost())
                .totalReturned(loanRepository.getTotalReturned())
                .totalBorrowed(loanRepository.getTotalBorrowed())
                .build();
    }

    public SimpleResponse insertLoan(CreateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Book book = bookService.getBookEntityById(request.getBookId());
        book.setStock(book.getStock() - 1);
        Student student = studentService.getStudentEntityById(request.getStudentId());
        if (loanRepository.existsByBookAndStudentAndIsReturned(book, student, false)){
            redirectAttributes.addFlashAttribute("alreadyHas", true);
            return SimpleResponse.builder()
                    .success(false)
                    .build();
        }
        if (loanRepository.findByStudentAndIsReturned(student, false).size() >= 5){
            redirectAttributes.addFlashAttribute("reachedLimit", true);
            return SimpleResponse.builder()
                    .success(false)
                    .build();
        }
        Loan loan = LoanMapper.createRequestToEntity(request);
        loan.setStudent(student);
        loan.setBook(book);
        loan.setStatus(LoanStatus.BORROWED);
        bookService.recordRestock(
                RestockRequest.builder()
                        .type(RestockType.LOAN)
                        .n(1)
                        .isAddition(false)
                        .bookId(request.getBookId())
                        .build()
        );
        loanRepository.save(loan);
        return SimpleResponse.builder()
                .success(true)
                .build();
    }

    public SimpleResponse updateLoan(UpdateLoanRequest request, RedirectAttributes redirectAttributes) throws Exception {
        Book book = bookService.getBookEntityById(request.getBookId());
        Student student = studentService.getStudentEntityById(request.getStudentId());
        if (loanRepository.existsByBookAndStudentAndIsReturned(book, student, false)){
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

    public void returnLoan(Long loanId) throws Exception {
        Loan loan = getLoanEntityById(loanId);
        loan.setIsReturned(true);
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
        bookService.restockBookById(loan.getBook().getId(), 1, RestockType.LOAN);
    }

    public void loseLoan(Long loanId) throws Exception {
        Loan loan = getLoanEntityById(loanId);

        if(loan.getStatus() == LoanStatus.RETURNED) {
            bookService.restockBookById(loan.getBook().getId(), -1, RestockType.LOAN);
        }

        loan.setIsReturned(false);
        loan.setStatus(LoanStatus.LOST);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public void neutralizeLoan(Long loanId) throws Exception {
        Loan loan = getLoanEntityById(loanId);

        if(loan.getStatus() == LoanStatus.RETURNED) {
            bookService.restockBookById(loan.getBook().getId(), -1, RestockType.LOAN);
        }

        loan.setIsReturned(false);
        loan.setStatus(LoanStatus.BORROWED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public Loan getLoanEntityById(Long loanId) throws Exception {
        return loanRepository.findById(loanId).orElseThrow(Exception::new);
    }
}
