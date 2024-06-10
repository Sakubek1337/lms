package com.iau.lms.models.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {

    Long id;

    String title;

    String author;

    Integer releaseYear;

    Integer stock;

    Integer loans;

    String fullTitle;
}
