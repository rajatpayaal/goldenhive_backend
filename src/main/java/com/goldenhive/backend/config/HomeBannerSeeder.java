package com.goldenhive.backend.config;

import com.goldenhive.backend.entity.HomeBanner;
import com.goldenhive.backend.repository.HomeBannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class HomeBannerSeeder implements CommandLineRunner {

    private final HomeBannerRepository homeBannerRepository;

    @Override
    public void run(String... args) {
        seedBannerIfMissing(1,
                "Rishikesh Adventure Packages for River Rafting, Camping and Weekend Escapes",
                "Book SEO-friendly Rishikesh adventure packages with rafting, camping, bungee jumping and flexible travel planning for friends, couples and families.",
                "/images/home-banners/rishikesh-adventure-packages.webp",
                "Rishikesh adventure packages banner with rafting and camping experience",
                "Explore Rishikesh",
                "/rishikesh-activities",
                "Rishikesh Adventure Packages 2026 | River Rafting, Camping and Stays",
                "Discover Rishikesh adventure packages with rafting, camping and curated stays. Ideal for weekend travellers looking for thrilling and scenic experiences.");

        seedBannerIfMissing(2,
                "Char Dham Yatra Packages with Comfortable Stays and Guided Planning",
                "Plan your Char Dham Yatra with trusted routes, comfortable stays, transport support and spiritually meaningful travel experiences across Uttarakhand.",
                "/images/home-banners/char-dham-yatra-packages.webp",
                "Char Dham Yatra packages banner for Kedarnath Badrinath Gangotri Yamunotri",
                "View Char Dham Tours",
                "/char-dham",
                "Char Dham Yatra Packages 2026 | Kedarnath Badrinath Tour Booking",
                "Explore Char Dham Yatra packages with route planning, stay options and reliable support for Kedarnath, Badrinath, Gangotri and Yamunotri travel.");

        seedBannerIfMissing(3,
                "Luxury Uttarakhand Stays, Riverside Resorts and Handpicked Travel Retreats",
                "Find premium stays in Rishikesh and Uttarakhand with riverside views, peaceful retreats and curated experiences for memorable getaways.",
                "/images/home-banners/luxury-uttarakhand-stays.webp",
                "Luxury Uttarakhand stays and riverside resort homepage banner",
                "Find Your Stay",
                "/stay",
                "Luxury Uttarakhand Stays | Riverside Resorts and Travel Retreats",
                "Browse luxury Uttarakhand stays, boutique resorts and peaceful riverside retreats designed for couples, families and premium leisure travel.");

        seedBannerIfMissing(4,
                "Custom Holiday Packages for Families, Couples and Group Trips in Uttarakhand",
                "Create personalised itineraries for family holidays, honeymoon trips and group adventures with local assistance and transparent planning.",
                "/images/home-banners/custom-holiday-packages-uttarakhand.webp",
                "Custom Uttarakhand holiday packages banner for family and couple trips",
                "Plan a Custom Trip",
                "/contact-us",
                "Custom Uttarakhand Holiday Packages | Family, Couple and Group Tours",
                "Book custom Uttarakhand holiday packages with flexible itineraries, curated stays and local travel support for family vacations, couple getaways and group tours.");
    }

    private void seedBannerIfMissing(int displayOrder,
                                     String title,
                                     String description,
                                     String imageUrl,
                                     String altText,
                                     String ctaLabel,
                                     String ctaLink,
                                     String seoTitle,
                                     String seoDescription) {

        homeBannerRepository.findByDisplayOrder(displayOrder)
                .ifPresentOrElse(existing -> log.info("Home banner already present for display order: {}", displayOrder),
                        () -> {
                            LocalDateTime now = LocalDateTime.now();
                            HomeBanner banner = new HomeBanner();
                            banner.setTitle(title);
                            banner.setDescription(description);
                            banner.setImageUrl(imageUrl);
                            banner.setAltText(altText);
                            banner.setCtaLabel(ctaLabel);
                            banner.setCtaLink(ctaLink);
                            banner.setSeoTitle(seoTitle);
                            banner.setSeoDescription(seoDescription);
                            banner.setDisplayOrder(displayOrder);
                            banner.setActive(true);
                            banner.setCreatedAt(now);
                            banner.setUpdatedAt(now);
                            homeBannerRepository.save(banner);
                            log.info("Home banner seeded for display order: {}", displayOrder);
                        });
    }
}
