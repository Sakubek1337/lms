package com.iau.lms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SimpleResponse {
    private String message;
    private Boolean success;
    private Object result;
}
