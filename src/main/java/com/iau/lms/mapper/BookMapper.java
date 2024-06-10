package com.iau.lms.mapper;

import com.iau.lms.models.dto.BookDto;
import com.iau.lms.models.dto.StudentDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.InsertBookRequest;

import java.util.List;

public class BookMapper {

    public static Book insertReqToBook(InsertBookRequest request){
        return Book.builder()
                .title(request.getName())
                .author(request.getAuthor())
                .releaseYear(request.getReleaseYear())
                .stock(request.getStock())
                .build();
    }

    public static BookDto entityToDto(Book book){
        return BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .stock(book.getStock())
                .releaseYear(book.getReleaseYear())
                .loans(book.getLoans().size())
                .fullTitle(book.getTitle() + " - " + book.getAuthor() + ", " + book.getReleaseYear())
                .build();
    }

    public static List<BookDto> entityListToDtoList(List<Book> entities){
        return entities.stream().map(BookMapper::entityToDto).toList();
    }
}
