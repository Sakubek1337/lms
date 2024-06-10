package com.iau.lms.models.dto;

import com.iau.lms.enums.LoanStatus;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanDto {

    Long id;

    String studentFullName;

    String bookTitle;

    String bookAuthor;

    String deadline;

    String creationDate;

    String returnDate;

    Boolean isDue;

    LoanStatus status;

    String statusColor;

}
