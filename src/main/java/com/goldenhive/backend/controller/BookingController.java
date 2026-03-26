package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.dto.BookingNoteRequest;
import com.goldenhive.backend.dto.CreateBookingRequest;
import com.goldenhive.backend.dto.UpdateBookingStatusRequest;
import com.goldenhive.backend.enums.BookingStatus;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IBookingService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "BOOKING APIs (7)", description = "Booking endpoints")
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping("/api/user/booking/create-from-cart")
    @Operation(summary = "[BOOKING] Create booking from cart")
    public ApiResponse<BookingDTO> create(@Valid @RequestBody CreateBookingRequest request) {
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking created").data(bookingService.createBookingFromCart(request)).build();
    }

    @GetMapping("/api/user/booking/my-bookings")
    @Operation(summary = "[BOOKING] List user bookings")
    public ApiResponse<List<BookingDTO>> myBookings(@RequestParam String userId) {
        return ApiResponse.<List<BookingDTO>>builder().success(true).message("Bookings fetched").data(bookingService.getUserBookings(userId)).build();
    }

    @GetMapping("/api/admin/bookings")
    @Operation(summary = "[BOOKING] List all admin bookings")
    public ApiResponse<List<BookingDTO>> adminBookings() {
        return ApiResponse.<List<BookingDTO>>builder().success(true).message("Bookings fetched").data(bookingService.getAllBookings()).build();
    }

    @GetMapping("/api/admin/bookings/{id}")
    @Operation(summary = "[BOOKING] Get booking by ID")
    public ApiResponse<BookingDTO> booking(@PathVariable String id) {
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking fetched").data(bookingService.getBookingById(id).orElseThrow(() -> new NotFoundException("Booking not found"))).build();
    }

    @PutMapping("/api/admin/bookings/{id}/status")
    @Operation(summary = "[BOOKING] Update booking status")
    public ApiResponse<BookingDTO> status(@PathVariable String id, @Valid @RequestBody UpdateBookingStatusRequest request) {
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking status updated").data(bookingService.updateBookingStatus(id, request.getStatus())).build();
    }

    @PutMapping("/api/admin/bookings/{id}/approve")
    @Operation(summary = "[BOOKING] Approve booking")
    public ApiResponse<BookingDTO> approve(@PathVariable String id) {
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking approved").data(bookingService.updateBookingStatus(id, BookingStatus.APPROVED)).build();
    }

    @PutMapping("/api/admin/bookings/{id}/reject")
    @Operation(summary = "[BOOKING] Reject booking")
    public ApiResponse<BookingDTO> reject(@PathVariable String id) {
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking rejected").data(bookingService.updateBookingStatus(id, BookingStatus.REJECTED)).build();
    }

    @PostMapping("/api/admin/bookings/{id}/notes")
    @Operation(summary = "[CRM] Add booking note", tags = {"CRM / NOTES APIs (2)"})
    public ApiResponse<BookingDTO> addNote(@PathVariable String id, @Valid @RequestBody BookingNoteRequest request) {
        BookingDTO booking = bookingService.addNote(id, request.getNote());
        if (request.getFollowUpDate() != null) {
            booking = bookingService.setFollowUpDate(id, request.getFollowUpDate());
        }
        return ApiResponse.<BookingDTO>builder().success(true).message("Booking note added").data(booking).build();
    }

    @GetMapping("/api/admin/bookings/{id}/notes")
    @Operation(summary = "[CRM] Get booking notes", tags = {"CRM / NOTES APIs (2)"})
    public ApiResponse<List<String>> getNotes(@PathVariable String id) {
        BookingDTO booking = bookingService.getBookingById(id).orElseThrow(() -> new NotFoundException("Booking not found"));
        return ApiResponse.<List<String>>builder().success(true).message("Booking notes fetched").data(booking.getNotes()).build();
    }
}



