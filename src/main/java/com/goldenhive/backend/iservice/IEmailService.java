package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.BookingDTO;

public interface IEmailService {
    void sendBookingConfirmation(String email, BookingDTO booking, byte[] pdfBytes);
    void sendAdminBookingAlert(BookingDTO booking);
}
