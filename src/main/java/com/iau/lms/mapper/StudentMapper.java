package com.iau.lms.mapper;

import com.iau.lms.models.dto.StudentDto;
import com.iau.lms.models.request.CreateStudentRequest;
import com.iau.lms.models.entity.Student;
import com.iau.lms.models.request.UpdateStudentRequest;

import java.util.List;

public class StudentMapper {

    public static Student convertCreateRequestToEntity(CreateStudentRequest req){
        return Student.builder()
                .pin(req.getPin())
                .grade(req.getGrade())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .graduated(req.getGrade() > 11)
                .build();
    }

    public static StudentDto entityToDto(Student student){
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .grade(student.getGrade())
                .gradeText(getGrade(student.getGrade()))
                .pin(student.getPin())
                .graduated(student.getGraduated() ? "YES" : "NO")
                .loans(student.getLoans().size())
                .fullName(student.getFirstName() + " " + student.getLastName())
                .fullCard(student.getFirstName() + " " + student.getLastName() +
                        " (" + student.getPin() + ")")
                .build();

    }

    public static List<StudentDto> entityListToDtoList(List<Student> entities){
        return entities.stream().map(StudentMapper::entityToDto).toList();
    }

    private static String getGrade(int grade){
        return switch (grade) {
            case 1 -> "1st grade";
            case 2 -> "2nd grade";
            case 3 -> "3rd grade";
            case 99 -> "Graduated";
            default -> grade + "th grade";
        };
    }

    public static UpdateStudentRequest entityToUpdateRequest(Student student) {
        return UpdateStudentRequest.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .grade(student.getGrade())
                .newPin(student.getPin())
                .oldPin(student.getPin())
                .build();
    }

    public static Student convertUpdateRequestToEntity(UpdateStudentRequest req) {
        return Student.builder()
                .pin(req.getNewPin())
                .grade(req.getGrade())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .graduated(req.getGrade() > 11)
                .build();
    }
}
