package com.iau.lms.mapper;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.InsertBookRequest;

public class BookMapper {

    public static Book insertReqToBook(InsertBookRequest request){
        return Book.builder()
                .name(request.getName())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .releaseYear(request.getReleaseYear())
                .stock(request.getStock())
                .build();
    }
}
