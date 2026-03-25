package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create Package Activity Mapping Request")
public class CreatePackageActivityMappingRequest {
    
    @NotBlank(message = "Package ID is required")
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc02")
    private String packageId;
    
    @NotBlank(message = "Activity ID is required")
    @Schema(description = "Activity ID", example = "60d5ec49c1234567890abc03")
    private String activityId;
    
    @NotNull(message = "Discounted price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Discounted price for this package", example = "3500")
    private double discountedPrice;
    
    @NotNull(message = "isIncluded flag is required")
    @Schema(description = "Is activity included in base package", example = "true")
    private boolean isIncluded;
}
