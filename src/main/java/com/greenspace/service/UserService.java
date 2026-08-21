package com.greenspace.service;

import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse getUserById(Long userId);
    UserResponse getUserByEmail(String email);
    void blockedUser(Long id);
}
