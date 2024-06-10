package com.iau.lms.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne
            @JoinColumn(referencedColumnName = "id", columnDefinition = "student_id")
    Student student;

    @ManyToOne
    Book book;

    LocalDate deadline;

    LocalDate creationDate;

    LocalDate returnDate;

    Boolean isReturned;
}
