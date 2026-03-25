package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Selected Activity Data Transfer Object")
public class SelectedActivityDTO {
    
    @Schema(description = "Activity ID", example = "60d5ec49c1234567890abc01")
    private String activityId;
    
    @Schema(description = "Activity name", example = "Scuba Diving")
    private String activityName;
    
    @Schema(description = "Original price", example = "5000")
    private double price;
    
    @Schema(description = "Discounted price for package", example = "3500")
    private double discountedPrice;
}
