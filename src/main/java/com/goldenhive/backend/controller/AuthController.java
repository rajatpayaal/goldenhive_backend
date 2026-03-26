package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.AuthResponse;
import com.goldenhive.backend.dto.LoginRequest;
import com.goldenhive.backend.dto.ProfileDTO;
import com.goldenhive.backend.dto.RegisterRequest;
import com.goldenhive.backend.iservice.IAuthService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "AUTH APIs (4)", description = "Authentication endpoints")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "[AUTH] Register user")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Registered", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.<AuthResponse>builder().success(true).message("Registration successful").data(authService.register(request)).build();
    }

    @PostMapping("/login")
    @Operation(summary = "[AUTH] User login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.<AuthResponse>builder().success(true).message("Login successful").data(authService.login(request)).build();
    }

    @PostMapping("/admin/login")
    @Operation(summary = "[AUTH] Admin login")
    public ApiResponse<AuthResponse> adminLogin(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.<AuthResponse>builder().success(true).message("Admin login successful").data(authService.adminLogin(request)).build();
    }

    @GetMapping("/profile")
    @Operation(summary = "[AUTH] Authenticated profile")
    public ApiResponse<ProfileDTO> profile(Authentication authentication) {
        return ApiResponse.<ProfileDTO>builder().success(true).message("Profile fetched").data(authService.getProfile(authentication.getName())).build();
    }
}



