package com.iau.lms.mapper;

import com.iau.lms.models.dto.VisitDto;
import com.iau.lms.models.entity.Visit;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class VisitMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm");

    public static List<VisitDto> entityListToDtoList(List<Visit> entities){
        return entities.stream().map(VisitMapper::entityToDto).collect(Collectors.toList());
    }

    public static VisitDto entityToDto(Visit entity){
        return VisitDto.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .studentFirstName(entity.getStudent().getFirstName())
                .studentLastName(entity.getStudent().getLastName())
                .time(formatter.format(entity.getTime()))
                .isCheckIn(entity.getIsCheckIn())
                .build();
    }
}
