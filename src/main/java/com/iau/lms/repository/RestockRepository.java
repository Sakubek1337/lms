package com.iau.lms.repository;

import com.iau.lms.models.entity.Restock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestockRepository extends JpaRepository<Restock, Long> {
    List<Restock> findAllByOrderByIdDesc();
}
