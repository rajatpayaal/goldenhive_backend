package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Package media data")
public class ImageDTO {
    
    @Schema(description = "Media URL", example = "https://example.com/media.jpg")
    private String url;
    
    @Schema(description = "Is primary media", example = "true")
    private boolean isPrimary;
}
