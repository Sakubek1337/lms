package com.iau.lms.service.impl;

import com.iau.lms.mapper.VisitMapper;
import com.iau.lms.models.response.SimpleVisitResponse;
import com.iau.lms.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl {

    private final VisitRepository visitRepository;

    public SimpleVisitResponse getInfo(){
        return SimpleVisitResponse.builder()
                .today(visitRepository.getTotalVisitsToday())
                .last7(visitRepository.getTotalVisitsLast7())
                .last30(visitRepository.getTotalVisitsLast30())
                .lastVisits(VisitMapper.entityListToDtoList(visitRepository.getLastVisits()))
                .build();
    }
}
