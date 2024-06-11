package com.iau.lms.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStudentRequest {

    @NotNull
            @Size(min = 100000000)
    Long pin;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotNull
            @Size(min = 0)
    Integer grade;

}
