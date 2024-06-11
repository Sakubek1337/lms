package com.iau.lms.models.request;

import com.iau.lms.models.dto.BookDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateLoanRequest {

    @NotNull
    Long studentId;

    @NotNull
    Long bookId;

    @NotNull
    LocalDate deadline;

}



