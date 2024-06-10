package com.iau.lms.mapper;

import com.iau.lms.models.dto.BookDto;
import com.iau.lms.models.dto.RestockDto;
import com.iau.lms.models.dto.StudentDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Restock;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.InsertBookRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookMapper {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

    public static RestockDto restockDto(Restock restock){
        return RestockDto.builder()
                .id(restock.getId())
                .creationDate(formatter.format(restock.getCreationDate()))
                .bookTitle(restock.getBook().getTitle())
                .bookAuthor(restock.getBook().getAuthor())
                .isAddition(restock.getIsAddition())
                .numberText(restock.getIsAddition() ? "+" + restock.getNumber() : "-" + restock.getNumber())
                .color(restock.getIsAddition() ? "green" : "red")
                .type(restock.getType())
                .build();
    }

    public static List<BookDto> entityListToDtoList(List<Book> entities){
        return entities.stream().map(BookMapper::entityToDto).toList();
    }

    public static List<RestockDto> restocksToDtos(List<Restock> entities){
        return entities.stream().map(BookMapper::restockDto).toList();
    }
}
