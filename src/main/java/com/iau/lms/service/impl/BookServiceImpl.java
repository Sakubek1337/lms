package com.iau.lms.service.impl;

import com.iau.lms.mapper.BookMapper;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.dto.BookDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.request.BooksResponse;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl {

    private final BookRepository bookRepository;

    public SimpleResponse insertBook(InsertBookRequest bookRequest){
        bookRepository.save(BookMapper.insertReqToBook(bookRequest));
        return new SimpleResponse("Book was inserted successfully!", true, null);
    }

    public void restockBookById(Long bookId, Integer n) throws Exception {
        Book book = getBookEntityById(bookId);
        book.setStock(book.getStock() + n);
        bookRepository.save(book);
    }

    public BooksResponse getBooks(){
        List<Book> bookList = bookRepository.findAll();
        return BooksResponse.builder()
                .books(bookList)
                .bookCount(bookRepository.count())
                .totalStock(bookRepository.getTotalStock())
                .build();
    }

    public List<Book> getCatalog(){
        return bookRepository.findAll();
    }

    public SimpleResponse getBookById(Long bookId) throws Exception {
        return SimpleResponse.builder()
                .result(BookMapper.entityToDto(getBookEntityById(bookId)))
                .build();
    }

    public Book getBookEntityById(Long id) throws Exception {
        return bookRepository.findById(id).orElseThrow(() -> new Exception());
    }

    public void updateBook(Long bookId, Book book) {
        book.setId(bookId);
        bookRepository.save(book);
    }

    public void deleteBookbyId(Long bookId){
        bookRepository.deleteById(bookId);
    }
}
