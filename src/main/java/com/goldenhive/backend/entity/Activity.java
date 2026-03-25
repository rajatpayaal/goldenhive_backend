package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    private String activityId;
    
    private String name;
    private String type;
    private double price;
    private int duration;
    private List<String> images;
    private String location;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
