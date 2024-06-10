package com.iau.lms.models.request;

import com.iau.lms.enums.RestockType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestockRequest {

    Long bookId;
    Integer n;
    boolean isAddition;
    RestockType type;

}



