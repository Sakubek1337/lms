package com.iau.lms.repository;

import com.iau.lms.models.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM visits WHERE time >= date_trunc('day', NOW());")
    Long getTotalVisitsToday();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM visits WHERE time >= date_trunc('day', NOW()) - INTERVAL '6 days';")
    Long getTotalVisitsLast7();

    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM visits WHERE time >= date_trunc('day', NOW()) - INTERVAL '29 days';")
    Long getTotalVisitsLast30();

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT * FROM visits ORDER BY id DESC LIMIT 10;")
    List<Visit> getLastVisits();
}
