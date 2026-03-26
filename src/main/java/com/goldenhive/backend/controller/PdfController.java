package com.goldenhive.backend.controller;

import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.IBookingService;
import com.goldenhive.backend.iservice.IPdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PDF APIs (1)", description = "PDF endpoints")
public class PdfController {
    private final IBookingService bookingService;
    private final IPdfService pdfService;

    @GetMapping("/api/booking/{id}/pdf")
    @Operation(summary = "[PDF] Download booking PDF")
    public ResponseEntity<byte[]> pdf(@PathVariable String id) {
        var booking = bookingService.getBookingById(id).orElseThrow(() -> new NotFoundException("Booking not found"));
        byte[] bytes = pdfService.generateBookingSummary(booking);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=booking-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}


