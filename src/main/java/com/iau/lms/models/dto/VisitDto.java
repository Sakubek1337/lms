package com.iau.lms.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitDto {
    Long id;
    String time;
    Long studentId;
    String studentFirstName;
    String studentLastName;
    Boolean isCheckIn;
}
