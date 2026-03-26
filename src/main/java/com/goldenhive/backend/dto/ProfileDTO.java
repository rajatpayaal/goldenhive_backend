package com.goldenhive.backend.dto;

import com.goldenhive.backend.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authenticated user profile")
public class ProfileDTO {
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
}
