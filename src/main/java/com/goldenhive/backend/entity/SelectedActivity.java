package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectedActivity {
    private String activityId;
    private String activityName;
    private double price;
    private double discountedPrice;
}
