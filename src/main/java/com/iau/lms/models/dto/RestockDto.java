package com.iau.lms.models.dto;

import com.iau.lms.enums.RestockType;
import com.iau.lms.models.entity.Book;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestockDto {

    Long id;

    String bookTitle;

    String bookAuthor;

    String creationDate;

    String numberText;

    String color;

    Boolean isAddition;

    RestockType type;
}
