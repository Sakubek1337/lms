package com.iau.lms.models.request;

import com.iau.lms.enums.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateRequest {

    @NotBlank
    String title;

    @NotBlank
    String author;

    @NotNull
    @Size(max = 2024)
    Integer releaseYear;

    @NotNull
    Integer stock;
}



