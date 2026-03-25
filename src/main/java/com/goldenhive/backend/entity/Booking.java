package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.goldenhive.backend.enums.BookingStatus;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    private String bookingId;
    
    private String userId;
    private String cartId;
    private String packageId;
    
    private List<SelectedActivity> selectedActivities;
    private double totalPrice;
    
    private LocalDate travelDate;
    private int travelers;
    
    private BookingStatus status;
    private List<String> notes;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime followUpDate;
}
