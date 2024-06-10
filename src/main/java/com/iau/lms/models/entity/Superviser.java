package com.iau.lms.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "supervisers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Superviser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;

    String lastName;

}
