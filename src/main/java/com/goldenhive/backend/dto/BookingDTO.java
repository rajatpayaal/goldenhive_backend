package com.goldenhive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.goldenhive.backend.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Booking Data Transfer Object")
public class BookingDTO {
    
    @Schema(description = "Booking ID", example = "60d5ec49c1234567890abc01")
    private String bookingId;
    
    @Schema(description = "User ID", example = "60d5ec49c1234567890abc02")
    private String userId;
    
    @Schema(description = "Cart ID", example = "60d5ec49c1234567890abc03")
    private String cartId;
    
    @Schema(description = "Package ID", example = "60d5ec49c1234567890abc04")
    private String packageId;
    
    @Schema(description = "Selected activities")
    private List<SelectedActivityDTO> selectedActivities;
    
    @Schema(description = "Total booking price", example = "65000")
    private double totalPrice;
    
    @Schema(description = "Travel date")
    private LocalDate travelDate;
    
    @Schema(description = "Number of travelers", example = "4")
    private int travelers;
    
    @Schema(description = "Booking status", example = "REQUESTED")
    private BookingStatus status;
    
    @Schema(description = "CRM notes")
    private List<String> notes;
    
    @Schema(description = "Booking creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Booking last update timestamp")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Follow-up date")
    private LocalDateTime followUpDate;
}
