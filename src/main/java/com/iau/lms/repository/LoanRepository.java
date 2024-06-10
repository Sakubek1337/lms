package com.iau.lms.repository;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM loans WHERE student_id = :sId ;")
    List<Loan> findByStudentId(@Param("sId") Long studentId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM loans WHERE book_id = :bId ;")
    List<Loan> findByBookId(@Param("bId") Long bookId);

    boolean existsByBookAndStudentAndIsReturned(Book book, Student student, boolean isReturned);

    List<Loan> findByStudentAndIsReturned(Student student, boolean isReturned);

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE creation_date >= date_trunc('day', NOW());")
    Long getTotalLoansToday();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE creation_date >= date_trunc('day', NOW()) - INTERVAL '6 days';")
    Long getTotalLoansLast7();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE creation_date >= date_trunc('day', NOW()) - INTERVAL '29 days';")
    Long getTotalLoansLast30();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE status = 2;")
    Long getTotalLost();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE status = 0;")
    Long getTotalReturned();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM loans WHERE status = 1;")
    Long getTotalBorrowed();
}
