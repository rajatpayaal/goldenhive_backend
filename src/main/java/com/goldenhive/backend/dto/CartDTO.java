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
@Schema(description = "Cart Data Transfer Object")
public class CartDTO {
    
    @Schema(description = "Cart ID", example = "60d5ec49c1234567890abc01")
    private String cartId;
    
    @Schema(description = "User ID", example = "60d5ec49c1234567890abc02")
    private String userId;
    
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc03")
    private String packageId;
    
    @Schema(description = "Selected activities in cart")
    private List<SelectedActivityDTO> selectedActivities;
    
    @Schema(description = "Total price (basePrice + activities)", example = "65000")
    private double totalPrice;
    
    @Schema(description = "Cart creation timestamp")
    private LocalDateTime createdAt;
}
