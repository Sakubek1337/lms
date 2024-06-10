package com.iau.lms.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    Long pin;

    String firstName;

    String lastName;

    Integer grade;

    Boolean graduated;

    @OneToMany(mappedBy = "student")
    List<Loan> loans;
}
