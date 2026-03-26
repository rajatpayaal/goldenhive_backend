package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.BookingDTO;
import com.goldenhive.backend.dto.SelectedActivityDTO;
import com.goldenhive.backend.iservice.IPdfService;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements IPdfService {
    @Override
    public byte[] generateBookingSummary(BookingDTO booking) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Booking Summary"));
            document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
            document.add(new Paragraph("Package ID: " + booking.getPackageId()));
            document.add(new Paragraph("Travel Date: " + booking.getTravelDate()));
            document.add(new Paragraph("Travelers: " + booking.getTravelers()));
            document.add(new Paragraph("Total Price: " + booking.getTotalPrice()));
            if (booking.getSelectedActivities() != null) {
                for (SelectedActivityDTO activity : booking.getSelectedActivities()) {
                    document.add(new Paragraph("Activity: " + activity.getActivityName() + " - " + activity.getDiscountedPrice()));
                }
            }
            document.close();
            return outputStream.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate PDF", ex);
        }
    }
}
