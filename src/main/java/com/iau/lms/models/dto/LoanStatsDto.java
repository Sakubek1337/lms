package com.iau.lms.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatsDto {
    Long last7days;
    Long last30days;
    Long total;
    Long totalReturned;
    Long totalLost;
    Long totalBorrowed;
}
