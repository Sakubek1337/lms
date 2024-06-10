package com.iau.lms.models.entity;

import com.iau.lms.enums.RestockType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "restocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Book book;

    LocalDate creationDate;

    Integer number;

    Boolean isAddition;

    @Enumerated(EnumType.STRING)
    RestockType type;
}
