package com.goldenhive.backend.config;

import com.goldenhive.backend.entity.User;
import com.goldenhive.backend.enums.Role;
import com.goldenhive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultAdminSeeder implements CommandLineRunner {

    private static final String DEFAULT_ADMIN_EMAIL = "rajat@yopmail.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "pass123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.findByEmail(DEFAULT_ADMIN_EMAIL)
                .ifPresentOrElse(this::ensureAdminRole, this::createDefaultAdmin);
    }

    private void createDefaultAdmin() {
        User admin = new User();
        admin.setFullName("Default Admin");
        admin.setEmail(DEFAULT_ADMIN_EMAIL);
        admin.setPhone("+910000000000");
        admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
        admin.setRole(Role.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        userRepository.save(admin);
        log.info("Default admin created successfully for email: {}", DEFAULT_ADMIN_EMAIL);
    }

    private void ensureAdminRole(User existingUser) {
        boolean changed = false;

        if (existingUser.getRole() != Role.ADMIN) {
            existingUser.setRole(Role.ADMIN);
            changed = true;
        }

        if (!passwordEncoder.matches(DEFAULT_ADMIN_PASSWORD, existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
            changed = true;
        }

        if (changed) {
            existingUser.setUpdatedAt(LocalDateTime.now());
            userRepository.save(existingUser);
            log.info("Existing user updated as default admin for email: {}", DEFAULT_ADMIN_EMAIL);
        } else {
            log.info("Default admin already present for email: {}", DEFAULT_ADMIN_EMAIL);
        }
    }
}
