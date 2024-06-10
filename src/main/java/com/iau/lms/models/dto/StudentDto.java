package com.iau.lms.models.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {

    Long id;

    Long pin;

    String firstName;

    String lastName;

    Integer grade;

    String gradeText;

    String graduated;

    Integer loans;

    String fullName;

    String fullCard;
}
