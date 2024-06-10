package com.iau.lms.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentStatsDto {
    Long total;
    Long graduated;
    Long current;
    List<Long> totalLoansByGrades;
}
