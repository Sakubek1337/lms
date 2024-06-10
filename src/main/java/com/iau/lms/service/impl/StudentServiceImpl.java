package com.iau.lms.service.impl;

import com.iau.lms.mapper.StudentMapper;
import com.iau.lms.models.SimpleResponse;
import com.iau.lms.models.dto.StudentDto;
import com.iau.lms.models.dto.StudentStatsDto;
import com.iau.lms.models.request.CreateStudentRequest;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.UpdateStudentRequest;
import com.iau.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final StudentRepository studentRepository;

    public SimpleResponse getStudents(){
        return SimpleResponse.builder()
                .result(StudentMapper.entityListToDtoList(studentRepository.findAllByOrderByFirstNameAscLastNameAsc()))
                .success(true)
                .build();

    }

    public List<StudentDto> getStudentsD(){
        return StudentMapper.entityListToDtoList(studentRepository.findAllByOrderByFirstNameAscLastNameAsc());
    }

    public SimpleResponse getStudentById(Long studentId) throws Exception {
        if (!studentRepository.existsById(studentId)){
            return SimpleResponse.builder()
                    .success(false)
                    .message("")
                    .build();
        }
        StudentDto studentDto = StudentMapper.entityToDto(getStudentEntityById(studentId));
        return SimpleResponse.builder()
                .success(true)
                .result(studentDto)
                .build();
    }

    public StudentStatsDto getStudentStats(){
        return StudentStatsDto.builder()
                .total(studentRepository.count())
                .graduated(studentRepository.getTotalGraduated())
                .current(studentRepository.count() - studentRepository.getTotalGraduated())
                .totalLoansByGrades(null)
                .build();
    }

    public SimpleResponse createStudent(CreateStudentRequest request, RedirectAttributes redirectAttributes){
        if (studentRepository.existsByPin(request.getPin())){
            redirectAttributes.addFlashAttribute("pinExists", true);
            return SimpleResponse.builder()
                        .success(false)
                        .build();
        }

        Student newStudent = StudentMapper.convertCreateRequestToEntity(request);
        studentRepository.save(newStudent);
        return SimpleResponse.builder()
                .message("SUCCESS")
                .success(true)
                .build();
    }

    public UpdateStudentRequest getUpdateRequestById(Long studentId) throws Exception {
        return StudentMapper.entityToUpdateRequest(getStudentEntityById(studentId));
    }

    public SimpleResponse updateStudent(long studentId, UpdateStudentRequest request, RedirectAttributes redirectAttributes){
        if (studentRepository.existsByPin(request.getNewPin()) && !request.getOldPin().equals(request.getNewPin())){
            redirectAttributes.addFlashAttribute("pinExists", true);
            return SimpleResponse.builder()
                    .success(false)
                    .build();
        }
        Student newStudent = StudentMapper.convertUpdateRequestToEntity(request);
        newStudent.setId(studentId);
        studentRepository.save(newStudent);
        return SimpleResponse.builder()
                .success(true)
                .build();
    }

    public SimpleResponse deleteStudent(Long studentId){
        System.out.println(studentId);
        if (!studentRepository.existsById(studentId)){
            return SimpleResponse.builder()
                    .success(false)
                    .message("")
                    .build();
        }
        return SimpleResponse.builder()
                .message("Student's record is moved to archive. After 30 days it will get permanently deleted!")
                .success(true)
                .build();
    }

    public SimpleResponse undo(Long studentId){
        return null;
    }

    public Student getStudentEntityById(Long id) throws Exception {
        return studentRepository.findById(id).orElseThrow(() -> new Exception());
    }
}
