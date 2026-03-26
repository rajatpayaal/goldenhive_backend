package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create Package Request using multipart/form-data")
public class CreatePackageFormRequest {

    @NotBlank(message = "Package name is required")
    @Schema(description = "Package name", example = "Taj Mahal Tours")
    private String name;

    @NotBlank(message = "Destination is required")
    @Schema(description = "Destination", example = "Agra, India")
    private String destination;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Duration in days", example = "5")
    private Integer duration;

    @NotNull(message = "Base price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Base price", example = "50000")
    private Double basePrice;

    @ArraySchema(schema = @Schema(type = "string", format = "binary", description = "Upload image/video/file"))
    private List<MultipartFile> images;

    @Schema(description = "Primary media index from uploaded files", example = "0")
    private Integer primaryImageIndex;

    @Schema(description = "JSON array string for inclusions", example = "[\"Hotel\",\"Breakfast\"]")
    private String inclusionsJson;

    @Schema(description = "JSON array string for exclusions", example = "[\"Flight\"]")
    private String exclusionsJson;

    @Schema(description = "JSON array string for itinerary", example = "[{\"day\":1,\"title\":\"Arrival\",\"description\":\"Check-in\",\"location\":\"Agra\"}]")
    private String itineraryJson;

    @Schema(description = "JSON array string for highlights", example = "[\"Sunrise view\",\"Private cab\"]")
    private String highlightsJson;

    @Schema(description = "Is customizable", example = "true")
    private Boolean customizable;

    @Schema(description = "WhatsApp contact number")
    private String whatsappContact;

    @Schema(description = "Popular tag")
    private String popularTag;

    @Schema(description = "Limited offer", example = "false")
    private Boolean limitedOffer;
}
