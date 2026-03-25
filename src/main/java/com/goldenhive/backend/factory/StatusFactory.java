package com.goldenhive.backend.factory;

import com.goldenhive.backend.enums.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Factory;

@Factory
@Slf4j
public class StatusFactory {
    
    /**
     * Validate if status transition is allowed
     */
    public boolean isValidTransition(BookingStatus currentStatus, BookingStatus newStatus) {
        log.info("Validating status transition: {} -> {}", currentStatus, newStatus);
        
        return switch (currentStatus) {
            case REQUESTED -> {
                // From REQUESTED, can go to CONTACTED, REJECTED
                yield newStatus == BookingStatus.CONTACTED || newStatus == BookingStatus.REJECTED;
            }
            case CONTACTED -> {
                // From CONTACTED, can go to NEGOTIATING, REJECTED
                yield newStatus == BookingStatus.NEGOTIATING || newStatus == BookingStatus.REJECTED;
            }
            case NEGOTIATING -> {
                // From NEGOTIATING, can go to APPROVED, REJECTED
                yield newStatus == BookingStatus.APPROVED || newStatus == BookingStatus.REJECTED;
            }
            case APPROVED -> {
                // From APPROVED, can only go to CLOSED
                yield newStatus == BookingStatus.CLOSED;
            }
            case REJECTED -> {
                // From REJECTED, can go to CLOSED
                yield newStatus == BookingStatus.CLOSED;
            }
            case CLOSED -> {
                // From CLOSED, no transitions allowed
                yield false;
            }
        };
    }
    
    /**
     * Get next possible statuses from current status
     */
    public BookingStatus[] getNextPossibleStatuses(BookingStatus currentStatus) {
        log.info("Getting next possible statuses for: {}", currentStatus);
        
        return switch (currentStatus) {
            case REQUESTED -> new BookingStatus[]{BookingStatus.CONTACTED, BookingStatus.REJECTED};
            case CONTACTED -> new BookingStatus[]{BookingStatus.NEGOTIATING, BookingStatus.REJECTED};
            case NEGOTIATING -> new BookingStatus[]{BookingStatus.APPROVED, BookingStatus.REJECTED};
            case APPROVED -> new BookingStatus[]{BookingStatus.CLOSED};
            case REJECTED -> new BookingStatus[]{BookingStatus.CLOSED};
            case CLOSED -> new BookingStatus[]{};
        };
    }
    
    /**
     * Get human-readable status description
     */
    public String getStatusDescription(BookingStatus status) {
        return switch (status) {
            case REQUESTED -> "Booking request received";
            case CONTACTED -> "Customer contacted";
            case NEGOTIATING -> "In negotiation phase";
            case APPROVED -> "Booking approved";
            case REJECTED -> "Booking rejected";
            case CLOSED -> "Booking closed";
        };
    }
    
    /**
     * Transition status with validation
     */
    public BookingStatus transitionStatus(BookingStatus currentStatus, BookingStatus desiredStatus) {
        log.info("Attempting status transition: {} -> {}", currentStatus, desiredStatus);
        
        if (!isValidTransition(currentStatus, desiredStatus)) {
            log.warn("Invalid transition attempted: {} -> {}", currentStatus, desiredStatus);
            throw new IllegalArgumentException(
                    String.format("Invalid status transition from %s to %s", currentStatus, desiredStatus)
            );
        }
        
        log.info("Status transition successful: {} -> {}", currentStatus, desiredStatus);
        return desiredStatus;
    }
}
