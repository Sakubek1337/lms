package com.iau.lms.repository;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Loan;
import com.iau.lms.models.entity.Student;
import org.hibernate.tuple.entity.EntityBasedAssociationAttribute;
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

    boolean existsByBookAndStudent(Book book, Student student);
}
