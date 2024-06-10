package com.iau.lms.models.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLoanRequest {

    Long studentId;

    Long bookId;

    Boolean isReturned;

    LocalDate deadline;

}



