package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Add to Cart Request")
public class AddToCartRequest {
    
    @Schema(description = "Authenticated user ID resolved from token", accessMode = Schema.AccessMode.READ_ONLY)
    private String userId;
    
    @NotBlank(message = "Package ID is required")
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc03")
    private String packageId;
    
    @Schema(description = "IDs of selected activities")
    private List<String> selectedActivityIds;
}
