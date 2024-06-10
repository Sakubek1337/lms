package com.iau.lms.models.request;

import com.iau.lms.enums.Subject;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InsertBookRequest {
    String name;

    String author;

    Integer releaseYear;

    Subject subject;

    Integer stock;
}
