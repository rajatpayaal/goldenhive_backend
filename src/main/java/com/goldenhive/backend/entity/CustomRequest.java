package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.goldenhive.backend.enums.CustomRequestStatus;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "custom_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomRequest {
    @Id
    private String requestId;
    
    private String userId;
    private List<String> destinations;
    private double budget;
    private int travelers;
    private String preferences;
    
    private CustomRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
