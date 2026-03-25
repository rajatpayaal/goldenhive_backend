package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private String cartId;
    
    private String userId;
    private String packageId;
    private List<SelectedActivity> selectedActivities;
    
    private double totalPrice;
    private LocalDateTime createdAt;
}
