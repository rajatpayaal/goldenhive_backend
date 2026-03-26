package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Custom package request payload")
public class CreateCustomRequestDTO {
    @NotBlank
    private String userId;
    @NotEmpty
    private List<String> destinations;
    @Positive
    private double budget;
    @Positive
    private int travelers;
    private String preferences;
}
