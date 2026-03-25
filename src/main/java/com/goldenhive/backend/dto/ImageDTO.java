package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Image Data Transfer Object")
public class ImageDTO {
    
    @Schema(description = "Image URL", example = "https://example.com/image.jpg")
    private String url;
    
    @Schema(description = "Is primary image", example = "true")
    private boolean isPrimary;
}
