package com.iau.lms.service.impl;

import com.iau.lms.enums.RestockType;
import com.iau.lms.mapper.BookMapper;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.dto.BookStatsDto;
import com.iau.lms.models.dto.RestockDto;
import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Restock;
import com.iau.lms.models.request.BooksResponse;
import com.iau.lms.models.request.InsertBookRequest;
import com.iau.lms.models.request.RestockRequest;
import com.iau.lms.repository.BookRepository;
import com.iau.lms.repository.RestockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl {

    private final BookRepository bookRepository;
    private final RestockRepository restockRepository;

    public SimpleResponse insertBook(InsertBookRequest bookRequest) throws Exception {
        Book book = bookRepository.save(BookMapper.insertReqToBook(bookRequest));
        RestockRequest request = RestockRequest.builder()
                .bookId(book.getId())
                .isAddition(true)
                .n(book.getStock())
                .type(RestockType.UPDATE)
                .build();
        recordRestock(request);
        return new SimpleResponse("Book was inserted successfully!", true, null);
    }

    public BookStatsDto getBookStats(){
        return BookStatsDto.builder()
                .total(bookRepository.count())
                .totalTitles(bookRepository.getTotalStock())
                .totalAuthors(bookRepository.getTotalAuthors())
                .build();
    }

    public void restockBookById(Long bookId, Integer n, RestockType type) throws Exception {
        Book book = getBookEntityById(bookId);
        book.setStock(book.getStock() + n);
        bookRepository.save(book);
        recordRestock(RestockRequest.builder()
                .bookId(bookId)
                .isAddition(n >= 0)
                .n(Math.abs(n))
                .type(type)
                .build());
    }

    public void recordRestock(RestockRequest request) throws Exception {
        Book book = getBookEntityById(request.getBookId());
        Restock restock = Restock.builder()
                .book(book)
                .type(request.getType())
                .number(request.getN())
                .isAddition(request.isAddition())
                .creationDate(LocalDate.now(ZoneId.of("Asia/Bishkek")))
                .build();
        restockRepository.save(restock);
    }

    public List<RestockDto> getRestocks(){
        return BookMapper.restocksToDtos(restockRepository.findAllByOrderByIdDesc());
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

    public void updateBook(Long bookId, Book book) throws Exception {
        Book oldBook = getBookEntityById(bookId);
        book.setId(bookId);
        if (!Objects.equals(book.getStock(), oldBook.getStock())) {
            RestockRequest request = RestockRequest.builder()
                    .bookId(bookId)
                    .isAddition(book.getStock() > oldBook.getStock())
                    .n(Math.abs(book.getStock() - oldBook.getStock()))
                    .type(RestockType.UPDATE)
                    .build();
            recordRestock(request);
        }
        bookRepository.save(book);
    }

    public void deleteBookbyId(Long bookId){
        bookRepository.deleteById(bookId);
    }
}
