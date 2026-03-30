package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "home_banners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeBanner {
    @Id
    private String bannerId;

    private String title;
    private String description;
    private String imageUrl;
    private String altText;
    private String ctaLabel;
    private String ctaLink;
    private String seoTitle;
    private String seoDescription;
    private int displayOrder;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
