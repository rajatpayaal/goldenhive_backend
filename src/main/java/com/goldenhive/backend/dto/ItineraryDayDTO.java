package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Itinerary Day Data Transfer Object")
public class ItineraryDayDTO {
    
    @Schema(description = "Day number", example = "1")
    private int day;
    
    @Schema(description = "Day title", example = "Arrival and City Tour")
    private String title;
    
    @Schema(description = "Day description")
    private String description;
    
    @Schema(description = "Location", example = "Delhi")
    private String location;
}
