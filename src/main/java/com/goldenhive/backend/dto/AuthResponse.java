package com.goldenhive.backend.dto;

import com.goldenhive.backend.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication response")
public class AuthResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9")
    private String token;
    @Schema(example = "rajat@example.com")
    private String email;
    @Schema(example = "Rajat Sharma")
    private String fullName;
    @Schema(example = "USER")
    private Role role;
}
