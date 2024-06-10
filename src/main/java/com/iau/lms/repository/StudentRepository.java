package com.iau.lms.repository;

import com.iau.lms.models.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByOrderByFirstNameAscLastNameAsc();
    boolean existsByPin(Long pin);
}
