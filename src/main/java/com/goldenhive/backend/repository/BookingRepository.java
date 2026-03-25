package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.Booking;
import com.goldenhive.backend.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    
    /**
     * Find all bookings for a user
     */
    List<Booking> findByUserId(String userId);
    
    /**
     * Find bookings by status
     */
    List<Booking> findByStatus(BookingStatus status);
    
    /**
     * Find user bookings by status
     */
    List<Booking> findByUserIdAndStatus(String userId, BookingStatus status);
    
    /**
     * Find bookings for a package
     */
    List<Booking> findByPackageId(String packageId);
    
    /**
     * Find all pending bookings
     */
    @Query("{'status': {$in: ['REQUESTED', 'CONTACTED', 'NEGOTIATING']}}")
    List<Booking> findPendingBookings();
    
    /**
     * Find bookings by status with pagination
     */
    Page<Booking> findByStatus(BookingStatus status, Pageable pageable);
    
    /**
     * Find bookings with follow-up date not yet passed
     */
    @Query("{'followUpDate': {$gte: new Date()}}")
    List<Booking> findBookingsWithUpcomingFollowUp();
    
    /**
     * Find bookings by travel date range
     */
    List<Booking> findByTravelDateBetween(LocalDate startDate, LocalDate endDate);
}
