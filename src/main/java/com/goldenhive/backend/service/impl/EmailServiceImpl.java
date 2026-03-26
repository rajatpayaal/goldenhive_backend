package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.iservice.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements IEmailService {
    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Override
    public void sendBookingConfirmation(String email, BookingDTO booking, byte[] pdfBytes) {
        sendWithAttachment(email, "Booking Confirmation", "Your booking " + booking.getBookingId() + " is confirmed.", pdfBytes);
    }

    @Override
    public void sendAdminBookingAlert(BookingDTO booking) {
        log.info("Admin notification prepared for booking {}", booking.getBookingId());
    }

    private void sendWithAttachment(String email, String subject, String text, byte[] bytes) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            log.info("JavaMailSender is not configured. Skipping email to {}", email);
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment("booking-summary.pdf", () -> new ByteArrayInputStream(bytes));
            mailSender.send(message);
        } catch (Exception ex) {
            log.warn("Email sending skipped/failed: {}", ex.getMessage());
        }
    }
}
