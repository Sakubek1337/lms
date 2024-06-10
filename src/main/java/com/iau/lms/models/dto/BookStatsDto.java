package com.iau.lms.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookStatsDto {
    Long total;
    Long totalTitles;
    Long totalAuthors;
}
