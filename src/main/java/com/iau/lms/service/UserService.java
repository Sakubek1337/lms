package com.iau.lms.service;

import com.iau.lms.models.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserDto getCurrentUser() throws Exception;
    boolean register(UserDto userDto, HttpServletRequest request);
}
