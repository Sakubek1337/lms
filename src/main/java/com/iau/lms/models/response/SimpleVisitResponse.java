package com.iau.lms.models.response;

import com.iau.lms.models.dto.VisitDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleVisitResponse {

    Long today;
    Long last7;
    Long last30;
    List<VisitDto> lastVisits;

}
