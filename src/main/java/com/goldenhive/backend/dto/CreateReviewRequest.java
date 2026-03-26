package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create review request")
public class CreateReviewRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String packageId;
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;
}
