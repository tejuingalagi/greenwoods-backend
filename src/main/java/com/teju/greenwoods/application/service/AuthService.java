package com.teju.greenwoods.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teju.greenwoods.application.dto.LoginRequest;
import com.teju.greenwoods.application.dto.LoginResponse;
import com.teju.greenwoods.application.exception.InvalidCredentialsException;
import com.teju.greenwoods.application.repository.AdminUserRepository;
import com.teju.greenwoods.domain.entity.AdminUser;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(AdminUserRepository adminUserRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        AdminUser admin = adminUserRepository.findByEmail(email)
                .filter(AdminUser::isActive)
                .filter(a -> passwordEncoder.matches(request.password(), a.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);

        String role = admin.getRole().name();
        String token = tokenProvider.generateToken(admin.getEmail(), role);

        return new LoginResponse(token, admin.getEmail(), role);
    }
}