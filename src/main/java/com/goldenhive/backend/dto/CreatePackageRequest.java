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
@Schema(description = "Create Package Request")
public class CreatePackageRequest {
    
    @NotBlank(message = "Package name is required")
    @Schema(description = "Package name", example = "Taj Mahal Tours")
    private String name;
    
    @NotBlank(message = "Destination is required")
    @Schema(description = "Destination", example = "Agra, India")
    private String destination;
    
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Duration in days", example = "5")
    private int duration;
    
    @NotNull(message = "Base price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Base price", example = "50000")
    private double basePrice;
    
    @Schema(description = "Package media files (images/videos)")
    private List<ImageDTO> images;
    
    @Schema(description = "Inclusions")
    private List<String> inclusions;
    
    @Schema(description = "Exclusions")
    private List<String> exclusions;
    
    @Schema(description = "Day-wise itinerary")
    private List<ItineraryDayDTO> itinerary;
    
    @Schema(description = "Package highlights")
    private List<String> highlights;
    
    @Schema(description = "Is customizable", example = "true")
    private boolean customizable;
    
    @Schema(description = "WhatsApp contact number")
    private String whatsappContact;
    
    @Schema(description = "Popular tag")
    private String popularTag;
    
    @Schema(description = "Limited offer", example = "false")
    private boolean limitedOffer;
}
