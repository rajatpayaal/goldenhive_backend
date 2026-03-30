package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Homepage banner data")
public class HomeBannerDTO {

    @Schema(description = "Banner ID", example = "67ef14f0c1234567890abc11")
    private String bannerId;

    @Schema(description = "Homepage banner title", example = "Rishikesh Adventure Packages for River Rafting and Camping")
    private String title;

    @Schema(description = "Short homepage banner description", example = "Explore rafting, camping, bungee jumping and weekend escapes in Rishikesh with easy booking and local support.")
    private String description;

    @Schema(description = "Banner image URL", example = "/images/home-banners/rishikesh-river-rafting.webp")
    private String imageUrl;

    @Schema(description = "SEO-friendly alt text for the banner image", example = "Rishikesh river rafting adventure packages banner")
    private String altText;

    @Schema(description = "CTA label", example = "Explore Rishikesh")
    private String ctaLabel;

    @Schema(description = "CTA link", example = "/rishikesh-activities")
    private String ctaLink;

    @Schema(description = "SEO title for the homepage banner", example = "Rishikesh Adventure Packages 2026 | Rafting Camping and More")
    private String seoTitle;

    @Schema(description = "SEO meta description for the homepage banner", example = "Book Rishikesh adventure packages with rafting, camping and curated stays. Perfect for couples, friends and weekend travellers.")
    private String seoDescription;

    @Schema(description = "Slider display order", example = "1")
    private int displayOrder;

    @Schema(description = "Whether the banner is active", example = "true")
    private boolean active;
}
