package com.iau.lms.models.request;

import com.iau.lms.enums.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitRequest {
    Long studentId;
    Boolean isCheckIn;
}
