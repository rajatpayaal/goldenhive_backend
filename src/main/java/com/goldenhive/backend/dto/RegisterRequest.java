package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User registration request")
public class RegisterRequest {
    @NotBlank
    @Schema(example = "Rajat Sharma")
    private String fullName;

    @Email
    @NotBlank
    @Schema(example = "rajat@example.com")
    private String email;

    @Schema(example = "+919876543210")
    private String phone;

    @NotBlank
    @Schema(example = "Password@123")
    private String password;
}
