package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDay {
    private int day;
    private String title;
    private String description;
    private String location;
}
