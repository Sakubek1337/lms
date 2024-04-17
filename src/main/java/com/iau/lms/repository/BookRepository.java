package com.iau.lms.repository;

import com.iau.lms.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(nativeQuery = true,
            value = "SELECT sum(stock) FROM books;")
    Long getTotalStock();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM books;")
    Long getTotalBooks();
}
