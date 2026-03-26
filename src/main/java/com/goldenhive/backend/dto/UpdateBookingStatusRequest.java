package com.goldenhive.backend.dto;

import com.goldenhive.backend.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Booking status update request")
public class UpdateBookingStatusRequest {
    @NotNull
    private BookingStatus status;
}
