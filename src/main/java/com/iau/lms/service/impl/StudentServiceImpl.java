package com.iau.lms.service.impl;

import com.iau.lms.models.entity.Book;
import com.iau.lms.models.entity.Student;
import com.iau.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final StudentRepository studentRepository;

    public Student getStudentEntityById(Long id) throws Exception {
        return studentRepository.findById(id).orElseThrow(() -> new Exception());
    }
}
