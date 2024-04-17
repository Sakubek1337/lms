package com.iau.lms.models.request;

import com.iau.lms.models.entity.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BooksResponse {
    List<Book> books;
    Integer bookCount;
}
