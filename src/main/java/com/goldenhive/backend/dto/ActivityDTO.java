package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Activity Data Transfer Object")
public class ActivityDTO {
    
    @Schema(description = "Activity ID", example = "60d5ec49c1234567890abc01")
    private String activityId;
    
    @Schema(description = "Activity name", example = "Scuba Diving")
    private String name;
    
    @Schema(description = "Activity type", example = "Water Sport")
    private String type;
    
    @Schema(description = "Price", example = "5000")
    private double price;
    
    @Schema(description = "Duration in hours", example = "3")
    private int duration;
    
    @Schema(description = "Activity images")
    private List<String> images;
    
    @Schema(description = "Location", example = "Maldives")
    private String location;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}
