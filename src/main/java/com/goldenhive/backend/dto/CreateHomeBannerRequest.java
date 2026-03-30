package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create or update home banner request")
public class CreateHomeBannerRequest {

    @NotBlank(message = "Title is required")
    @Schema(description = "Homepage banner title", example = "Rishikesh Adventure Packages for River Rafting and Camping")
    private String title;

    @NotBlank(message = "Description is required")
    @Schema(description = "Short homepage banner description", example = "Explore rafting, camping, bungee jumping and weekend escapes in Rishikesh with easy booking and local support.")
    private String description;

    @NotBlank(message = "Image URL is required")
    @Schema(description = "Banner image URL", example = "/images/home-banners/rishikesh-river-rafting.webp")
    private String imageUrl;

    @NotBlank(message = "Alt text is required")
    @Schema(description = "SEO-friendly alt text", example = "Rishikesh river rafting adventure packages banner")
    private String altText;

    @NotBlank(message = "CTA label is required")
    @Schema(description = "CTA label", example = "Explore Rishikesh")
    private String ctaLabel;

    @NotBlank(message = "CTA link is required")
    @Schema(description = "CTA link", example = "/rishikesh-activities")
    private String ctaLink;

    @NotBlank(message = "SEO title is required")
    @Schema(description = "SEO title", example = "Rishikesh Adventure Packages 2026 | River Rafting, Camping and Stays")
    private String seoTitle;

    @NotBlank(message = "SEO description is required")
    @Schema(description = "SEO description", example = "Discover Rishikesh adventure packages with rafting, camping and curated stays. Ideal for weekend travellers looking for thrilling and scenic experiences.")
    private String seoDescription;

    @NotNull(message = "Display order is required")
    @Min(value = 1, message = "Display order must be at least 1")
    @Schema(description = "Slider display order", example = "1")
    private Integer displayOrder;

    @NotNull(message = "Active flag is required")
    @Schema(description = "Whether the banner is active for public homepage slider", example = "true")
    private Boolean active;
}
