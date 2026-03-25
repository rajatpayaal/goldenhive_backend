package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "package_activity_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageActivityMapping {
    @Id
    private String id;
    
    private String packageId;
    private String activityId;
    private double discountedPrice;
    private boolean isIncluded;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
