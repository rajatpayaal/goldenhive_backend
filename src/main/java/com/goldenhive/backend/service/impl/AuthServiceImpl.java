package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.AuthResponse;
import com.goldenhive.backend.dto.LoginRequest;
import com.goldenhive.backend.dto.ProfileDTO;
import com.goldenhive.backend.dto.RegisterRequest;
import com.goldenhive.backend.entity.User;
import com.goldenhive.backend.enums.Role;
import com.goldenhive.backend.exception.BadRequestException;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.exception.UnauthorizedException;
import com.goldenhive.backend.iservice.IAuthService;
import com.goldenhive.backend.repository.UserRepository;
import com.goldenhive.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        Role role = request.getRole() == null ? Role.USER : request.getRole();
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(saved), saved.getEmail(), saved.getFullName(), saved.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = loadUserByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new AuthResponse(jwtService.generateToken(user), user.getEmail(), user.getFullName(), user.getRole());
    }

    @Override
    public AuthResponse adminLogin(LoginRequest request) {
        User user = loadUserByEmail(request.getEmail());
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.SALES_AGENT) {
            throw new UnauthorizedException("Admin access required");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new AuthResponse(jwtService.generateToken(user), user.getEmail(), user.getFullName(), user.getRole());
    }

    @Override
    public ProfileDTO getProfile(String email) {
        User user = loadUserByEmail(email);
        return new ProfileDTO(user.getUserId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getRole());
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }
}
