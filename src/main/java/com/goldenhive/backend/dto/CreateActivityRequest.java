package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create Activity Request")
public class CreateActivityRequest {
    
    @NotBlank(message = "Activity name is required")
    @Schema(description = "Activity name", example = "Scuba Diving")
    private String name;
    
    @NotBlank(message = "Activity type is required")
    @Schema(description = "Activity type", example = "Water Sport")
    private String type;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Price", example = "5000")
    private double price;
    
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Duration in hours", example = "3")
    private int duration;
    
    @Schema(description = "Activity images")
    private List<String> images;
    
    @NotBlank(message = "Location is required")
    @Schema(description = "Location", example = "Maldives")
    private String location;
}
