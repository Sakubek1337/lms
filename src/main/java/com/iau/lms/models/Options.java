package com.iau.lms.models;

import com.iau.lms.models.dto.BookDto;
import com.iau.lms.models.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Options {
    private List<StudentDto> students;
    private List<BookDto> books;
}
