package com.iau.lms.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthStatus {
    private boolean usernameUsed;
    private boolean registration;
}
