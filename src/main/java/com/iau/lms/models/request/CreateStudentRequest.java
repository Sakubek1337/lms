package com.iau.lms.models.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStudentRequest {

    Long pin;

    String firstName;

    String lastName;

    Integer grade;

}
