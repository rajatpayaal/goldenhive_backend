package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create Booking Request")
public class CreateBookingRequest {
    
    @NotBlank(message = "User ID is required")
    @Schema(description = "User ID", example = "60d5ec49c1234567890abc02")
    private String userId;
    
    @NotBlank(message = "Cart ID is required")
    @Schema(description = "Cart ID", example = "60d5ec49c1234567890abc03")
    private String cartId;
    
    @NotNull(message = "Travel date is required")
    @Schema(description = "Travel date")
    private LocalDate travelDate;
    
    @NotNull(message = "Number of travelers is required")
    @Positive(message = "Travelers must be at least 1")
    @Schema(description = "Number of travelers", example = "4")
    private int travelers;
}
