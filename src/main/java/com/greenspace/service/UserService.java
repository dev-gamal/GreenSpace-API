package com.greenspace.service;

import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse getUserById(Long userId);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    void blockedUser(Long id);
}
