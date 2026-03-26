package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.NotificationDTO;
import com.goldenhive.backend.iservice.INotificationQueryService;
import com.goldenhive.backend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements INotificationQueryService {
    private final BookingRepository bookingRepository;

    @Override
    public List<NotificationDTO> getAdminNotifications() {
        return bookingRepository.findPendingBookings().stream()
                .map(booking -> new NotificationDTO(
                        "BOOKING",
                        "Pending booking " + booking.getBookingId(),
                        booking.getUserId(),
                        "Booking is currently in status " + booking.getStatus(),
                        LocalDateTime.now()
                ))
                .toList();
    }
}
