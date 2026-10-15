package com.teju.greenwoods.application.repository;

import java.util.Optional;

import com.teju.greenwoods.domain.entity.AdminUser;

public interface AdminUserRepository {

    AdminUser save(AdminUser adminUser);

    Optional<AdminUser> findByEmail(String email);

    boolean existsByEmail(String email);
}