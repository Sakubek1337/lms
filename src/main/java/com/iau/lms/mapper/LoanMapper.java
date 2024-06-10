package com.iau.lms.mapper;

import com.iau.lms.enums.LoanStatus;
import com.iau.lms.models.dto.LoanDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.request.CreateLoanRequest;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.models.request.UpdateLoanRequest;
import com.iau.lms.models.request.UpdateStudentRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoanMapper {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LoanDto entityToDto(Loan loan){
        return LoanDto.builder()
                .studentFullName(loan.getStudent().getFirstName() + " " + loan.getStudent().getLastName())
                .bookTitle(loan.getBook().getTitle())
                .bookAuthor(loan.getBook().getAuthor())
                .creationDate(formatter.format(loan.getCreationDate()))
                .deadline(formatter.format(loan.getDeadline()))
                .status(loan.getStatus())
                .statusColor(getStatusColor(loan.getStatus()))
                .returnDate(loan.getIsReturned() ? formatter.format(loan.getReturnDate()) : "-")
                .id(loan.getId())
                .isDue(loan.getDeadline().isBefore(LocalDate.now(ZoneId.of("Asia/Bishkek"))))
                .build();
    }

    public static List<LoanDto> entityListToDtoList(List<Loan> entities){
        return entities.stream().map(LoanMapper::entityToDto).toList();
    }

    public static Loan createRequestToEntity(CreateLoanRequest request){
        return Loan.builder()
                .deadline(request.getDeadline())
                .creationDate(LocalDate.now(ZoneId.of("Asia/Bishkek")))
                .isReturned(false)
                .build();
    }

    public static Loan updateRequestToEntity(UpdateLoanRequest request){
        return Loan.builder()
                .deadline(request.getDeadline())
                .isReturned(request.getIsReturned())
                .build();
    }

    private static String getStatusColor(LoanStatus status){
        return switch (status){
            case LOST -> "red";
            case BORROWED -> "black";
            case RETURNED -> "green";
        };
    }
}
