package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.BookingDTO;

public interface IPdfService {
    byte[] generateBookingSummary(BookingDTO booking);
}
