package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.dto.CreateBookingRequest;
import com.goldenhive.backend.dto.SelectedActivityDTO;
import com.goldenhive.backend.entity.Booking;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.exception.UnauthorizedException;
import com.goldenhive.backend.enums.BookingStatus;
import com.goldenhive.backend.factory.BookingFactory;
import com.goldenhive.backend.factory.StatusFactory;
import com.goldenhive.backend.iservice.IBookingService;
import com.goldenhive.backend.iservice.ICartService;
import com.goldenhive.backend.iservice.IEmailService;
import com.goldenhive.backend.iservice.IPdfService;
import com.goldenhive.backend.repository.BookingRepository;
import com.goldenhive.backend.repository.CartRepository;
import com.goldenhive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {
    
    private final BookingRepository bookingRepository;
    private final CartRepository cartRepository;
    private final ICartService cartService;
    private final BookingFactory bookingFactory;
    private final StatusFactory statusFactory;
    private final UserRepository userRepository;
    private final IEmailService emailService;
    private final IPdfService pdfService;
    
    @Override
    public BookingDTO createBookingFromCart(CreateBookingRequest request) {
        log.info("Creating booking from cart - CartID: {}, User: {}", request.getCartId(), request.getUserId());
        
        // Fetch cart
        var cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart not found with ID: " + request.getCartId()));
        
        // Validate user
        if (!cart.getUserId().equals(request.getUserId())) {
            throw new UnauthorizedException("Cart does not belong to this user");
        }
        
        Booking booking = bookingFactory.createBookingFromCart(cart, request);
        bookingFactory.validateBooking(booking);
        
        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", savedBooking.getBookingId());
        
        // Clear cart after booking
        cartRepository.deleteById(request.getCartId());
        log.info("Cart cleared after booking");

        BookingDTO bookingDTO = mapToDTO(savedBooking);
        userRepository.findById(savedBooking.getUserId())
                .ifPresent(user -> emailService.sendBookingConfirmation(user.getEmail(), bookingDTO, pdfService.generateBookingSummary(bookingDTO)));
        emailService.sendAdminBookingAlert(bookingDTO);
        return bookingDTO;
    }
    
    @Override
    public Optional<BookingDTO> getBookingById(String bookingId) {
        log.info("Fetching booking with ID: {}", bookingId);
        return bookingRepository.findById(bookingId).map(this::mapToDTO);
    }
    
    @Override
    public List<BookingDTO> getUserBookings(String userId) {
        log.info("Fetching all bookings for User: {}", userId);
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BookingDTO> getAllBookings() {
        log.info("Fetching all bookings (admin)");
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BookingDTO> getBookingsByStatus(BookingStatus status) {
        log.info("Fetching bookings with status: {}", status);
        return bookingRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BookingDTO> getPendingBookings() {
        log.info("Fetching pending bookings");
        return bookingRepository.findPendingBookings()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public BookingDTO updateBookingStatus(String bookingId, BookingStatus newStatus) {
        log.info("Updating booking status - ID: {}, NewStatus: {}", bookingId, newStatus);
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
        
        booking.setStatus(statusFactory.transitionStatus(booking.getStatus(), newStatus));
        booking.setUpdatedAt(LocalDateTime.now());
        
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("Booking status updated");
        
        return mapToDTO(updatedBooking);
    }
    
    @Override
    public BookingDTO addNote(String bookingId, String note) {
        log.info("Adding note to booking - ID: {}", bookingId);
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
        
        if (booking.getNotes() == null) {
            booking.setNotes(new ArrayList<>());
        }
        
        booking.getNotes().add(note);
        booking.setUpdatedAt(LocalDateTime.now());
        
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("Note added to booking");
        
        return mapToDTO(updatedBooking);
    }
    
    @Override
    public BookingDTO setFollowUpDate(String bookingId, LocalDate followUpDate) {
        log.info("Setting follow-up date - ID: {}, Date: {}", bookingId, followUpDate);
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
        
        booking.setFollowUpDate(followUpDate.atStartOfDay());
        booking.setUpdatedAt(LocalDateTime.now());
        
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("Follow-up date set");
        
        return mapToDTO(updatedBooking);
    }
    
    @Override
    public List<BookingDTO> getBookingsWithUpcomingFollowUp() {
        log.info("Fetching bookings with upcoming follow-up");
        return bookingRepository.findBookingsWithUpcomingFollowUp()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BookingDTO> getBookingsByTravelDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching bookings in date range: {} to {}", startDate, endDate);
        return bookingRepository.findByTravelDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void cancelBooking(String bookingId) {
        log.info("Cancelling booking with ID: {}", bookingId);
        
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
        
        booking.setStatus(BookingStatus.CLOSED);
        booking.setUpdatedAt(LocalDateTime.now());
        
        bookingRepository.save(booking);
        log.info("Booking cancelled");
    }
    
    /**
     * Convert Booking entity to BookingDTO
     */
    private BookingDTO mapToDTO(Booking booking) {
        List<SelectedActivityDTO> selectedActivityDTOs = booking.getSelectedActivities() != null ?
                booking.getSelectedActivities().stream()
                        .map(sa -> new SelectedActivityDTO(
                                sa.getActivityId(),
                                sa.getActivityName(),
                                sa.getPrice(),
                                sa.getDiscountedPrice()
                        ))
                        .collect(Collectors.toList())
                : new ArrayList<>();
        
        return new BookingDTO(
                booking.getBookingId(),
                booking.getUserId(),
                booking.getCartId(),
                booking.getPackageId(),
                selectedActivityDTOs,
                booking.getTotalPrice(),
                booking.getTravelDate(),
                booking.getTravelers(),
                booking.getStatus(),
                booking.getNotes(),
                booking.getCreatedAt(),
                booking.getUpdatedAt(),
                booking.getFollowUpDate()
        );
    }
}
