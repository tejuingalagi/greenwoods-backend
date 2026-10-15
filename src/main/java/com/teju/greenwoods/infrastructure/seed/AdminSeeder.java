package com.teju.greenwoods.infrastructure.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.teju.greenwoods.application.repository.AdminUserRepository;
import com.teju.greenwoods.domain.entity.AdminUser;
import com.teju.greenwoods.domain.enums.Role;

@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminSeeder(AdminUserRepository adminUserRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${app.admin.email:}") String adminEmail,
                       @Value("${app.admin.password:}") String adminPassword) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.warn("ADMIN_EMAIL or ADMIN_PASSWORD is not set. No admin user was created.");
            return;
        }
        if (adminPassword.length() < 8) {
            log.warn("ADMIN_PASSWORD must be at least 8 characters. No admin user was created.");
            return;
        }

        String email = adminEmail.trim().toLowerCase();
        if (adminUserRepository.existsByEmail(email)) {
            log.info("Admin user already exists: {}", email);
            return;
        }

        AdminUser admin = new AdminUser();
        admin.setEmail(email);
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        adminUserRepository.save(admin);

        log.info("Admin user created: {}", email);
    }
}