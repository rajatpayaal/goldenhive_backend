package com.goldenhive.backend.factory;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.dto.CreateBookingRequest;
import com.goldenhive.backend.entity.Booking;
import com.goldenhive.backend.entity.Cart;
import com.goldenhive.backend.enums.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Factory;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Factory
@Slf4j
public class BookingFactory {
    
    /**
     * Create Booking entity from Cart and CreateBookingRequest
     */
    public Booking createBookingFromCart(Cart cart, CreateBookingRequest request) {
        log.info("Creating booking from cart - CartID: {}", cart.getCartId());
        
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        
        if (request == null) {
            throw new IllegalArgumentException("CreateBookingRequest cannot be null");
        }
        
        if (request.getTravelers() <= 0) {
            throw new IllegalArgumentException("Number of travelers must be at least 1");
        }
        
        Booking booking = new Booking();
        
        // Set basic info
        booking.setUserId(request.getUserId());
        booking.setCartId(request.getCartId());
        booking.setPackageId(cart.getPackageId());
        
        // Set pricing and activities
        booking.setSelectedActivities(cart.getSelectedActivities());
        booking.setTotalPrice(cart.getTotalPrice());
        
        // Set travel details
        booking.setTravelDate(request.getTravelDate());
        booking.setTravelers(request.getTravelers());
        
        // Set booking status and metadata
        booking.setStatus(BookingStatus.REQUESTED);
        booking.setNotes(new ArrayList<>());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        
        log.info("Booking created successfully from cart");
        return booking;
    }
    
    /**
     * Create Booking with minimal data (for testing/advanced cases)
     */
    public Booking createMinimalBooking(String userId, String packageId, double totalPrice) {
        log.info("Creating minimal booking - User: {}, Package: {}", userId, packageId);
        
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        
        if (packageId == null || packageId.isEmpty()) {
            throw new IllegalArgumentException("Package ID cannot be null or empty");
        }
        
        if (totalPrice <= 0) {
            throw new IllegalArgumentException("Total price must be positive");
        }
        
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPackageId(packageId);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.REQUESTED);
        booking.setNotes(new ArrayList<>());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        
        log.info("Minimal booking created");
        return booking;
    }
    
    /**
     * Validate booking before saving
     */
    public void validateBooking(Booking booking) {
        log.info("Validating booking");
        
        if (booking.getUserId() == null || booking.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        
        if (booking.getPackageId() == null || booking.getPackageId().isEmpty()) {
            throw new IllegalArgumentException("Package ID is required");
        }
        
        if (booking.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Total price must be positive");
        }
        
        if (booking.getTravelDate() == null) {
            throw new IllegalArgumentException("Travel date is required");
        }
        
        if (booking.getTravelers() <= 0) {
            throw new IllegalArgumentException("Number of travelers must be at least 1");
        }
        
        if (booking.getStatus() == null) {
            throw new IllegalArgumentException("Booking status is required");
        }
        
        log.info("Booking validation successful");
    }
    
    /**
     * Clone booking for copy/duplicate purposes
     */
    public Booking cloneBooking(Booking originalBooking) {
        log.info("Cloning booking: {}", originalBooking.getBookingId());
        
        Booking clonedBooking = new Booking();
        clonedBooking.setUserId(originalBooking.getUserId());
        clonedBooking.setPackageId(originalBooking.getPackageId());
        clonedBooking.setSelectedActivities(originalBooking.getSelectedActivities());
        clonedBooking.setTotalPrice(originalBooking.getTotalPrice());
        clonedBooking.setTravelDate(originalBooking.getTravelDate());
        clonedBooking.setTravelers(originalBooking.getTravelers());
        clonedBooking.setStatus(BookingStatus.REQUESTED);
        clonedBooking.setNotes(new ArrayList<>(originalBooking.getNotes() != null ? originalBooking.getNotes() : new ArrayList<>()));
        clonedBooking.setCreatedAt(LocalDateTime.now());
        clonedBooking.setUpdatedAt(LocalDateTime.now());
        
        log.info("Booking cloned successfully");
        return clonedBooking;
    }
}
