package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    @Id
    private String packageId;
    
    private String name;
    private String slug;
    private String destination;
    private int duration;
    private double basePrice;
    
    private List<Image> images;
    private List<String> inclusions;
    private List<String> exclusions;
    private List<ItineraryDay> itinerary;
    private List<String> highlights;
    
    private boolean customizable;
    private String whatsappContact;
    private String popularTag;
    private boolean limitedOffer;
    
    private Meta meta;
}
