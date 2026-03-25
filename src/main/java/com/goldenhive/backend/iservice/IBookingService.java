package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.dto.CreateBookingRequest;
import com.goldenhive.backend.enums.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookingService {
    
    /**
     * Create booking from cart
     */
    BookingDTO createBookingFromCart(CreateBookingRequest request);
    
    /**
     * Get booking by ID
     */
    Optional<BookingDTO> getBookingById(String bookingId);
    
    /**
     * Get all bookings for a user
     */
    List<BookingDTO> getUserBookings(String userId);
    
    /**
     * Get all bookings (admin)
     */
    List<BookingDTO> getAllBookings();
    
    /**
     * Get bookings by status
     */
    List<BookingDTO> getBookingsByStatus(BookingStatus status);
    
    /**
     * Get pending bookings (REQUESTED, CONTACTED, NEGOTIATING)
     */
    List<BookingDTO> getPendingBookings();
    
    /**
     * Update booking status
     */
    BookingDTO updateBookingStatus(String bookingId, BookingStatus newStatus);
    
    /**
     * Add note to booking (CRM)
     */
    BookingDTO addNote(String bookingId, String note);
    
    /**
     * Set follow-up date for booking
     */
    BookingDTO setFollowUpDate(String bookingId, LocalDate followUpDate);
    
    /**
     * Get bookings with upcoming follow-up
     */
    List<BookingDTO> getBookingsWithUpcomingFollowUp();
    
    /**
     * Get bookings for date range
     */
    List<BookingDTO> getBookingsByTravelDateRange(LocalDate startDate, LocalDate endDate);
    
    /**
     * Cancel booking
     */
    void cancelBooking(String bookingId);
}
