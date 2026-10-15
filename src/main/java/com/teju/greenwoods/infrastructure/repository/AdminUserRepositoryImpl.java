package com.teju.greenwoods.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teju.greenwoods.application.repository.AdminUserRepository;
import com.teju.greenwoods.domain.entity.AdminUser;

@Repository
public interface AdminUserRepositoryImpl extends JpaRepository<AdminUser, Long>, AdminUserRepository {
}