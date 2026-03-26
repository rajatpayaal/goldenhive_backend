package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Package Data Transfer Object")
public class PackageDTO {
    
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc01")
    private String packageId;
    
    @Schema(description = "Package name", example = "Taj Mahal Tours")
    private String name;
    
    @Schema(description = "SEO-friendly slug", example = "taj-mahal-tours")
    private String slug;
    
    @Schema(description = "Destination", example = "Agra, India")
    private String destination;
    
    @Schema(description = "Duration in days", example = "5")
    private int duration;
    
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
    
    @Schema(description = "WhatsApp contact")
    private String whatsappContact;
    
    @Schema(description = "Popular tag")
    private String popularTag;
    
    @Schema(description = "Limited offer")
    private boolean limitedOffer;
    
    @Schema(description = "Meta information")
    private MetaDTO meta;
}
