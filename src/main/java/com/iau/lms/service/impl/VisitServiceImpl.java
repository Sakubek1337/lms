package com.iau.lms.service.impl;

import com.iau.lms.mapper.VisitMapper;
import com.iau.lms.models.dto.VisitDto;
import com.iau.lms.models.entity.Visit;
import com.iau.lms.models.request.VisitRequest;
import com.iau.lms.models.response.SimpleVisitResponse;
import com.iau.lms.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl {

    private final VisitRepository visitRepository;
    private final StudentServiceImpl studentService;

    public SimpleVisitResponse getInfo(){
        return SimpleVisitResponse.builder()
                .today(visitRepository.getTotalVisitsToday())
                .last7(visitRepository.getTotalVisitsLast7())
                .last30(visitRepository.getTotalVisitsLast30())
                .lastVisits(VisitMapper.entityListToDtoList(visitRepository.getLastVisits()))
                .build();
    }

    public void recordVisit(VisitRequest visitRequest) throws Exception {
        visitRepository.save(Visit.builder()
                        .time(LocalDateTime.now(ZoneId.of("Asia/Bishkek")))
                        .student(studentService.getStudentEntityById(visitRequest.getStudentId()))
                        .isCheckIn(visitRequest.getIsCheckIn())
                .build());
    }

    public List<VisitDto> getVisits(){
        return VisitMapper.entityListToDtoList(visitRepository.findAll());
    }
}
