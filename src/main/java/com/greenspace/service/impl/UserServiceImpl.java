package com.greenspace.service.impl;

import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;
import com.greenspace.entity.User;
import com.greenspace.mapper.UserMapper;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("This email exists.");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsBlocked(false);

        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public void blockedUser(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setIsBlocked(!user.getIsBlocked());
        userRepository.save(user);
    }
}
