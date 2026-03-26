package com.goldenhive.backend.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldenhive.backend.dto.CreatePackageFormRequest;
import com.goldenhive.backend.dto.CreatePackageRequest;
import com.goldenhive.backend.dto.ImageDTO;
import com.goldenhive.backend.dto.ItineraryDayDTO;
import com.goldenhive.backend.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PackageFormDataHelper {

    private final ObjectMapper objectMapper;

    public CreatePackageRequest toCreatePackageRequest(CreatePackageFormRequest request, List<String> imageUrls) {
        return new CreatePackageRequest(
                request.getName(),
                request.getDestination(),
                request.getDuration(),
                request.getBasePrice(),
                buildImages(imageUrls, request.getPrimaryImageIndex()),
                parseStringList(request.getInclusionsJson(), "inclusionsJson"),
                parseStringList(request.getExclusionsJson(), "exclusionsJson"),
                parseItinerary(request.getItineraryJson()),
                parseStringList(request.getHighlightsJson(), "highlightsJson"),
                Boolean.TRUE.equals(request.getCustomizable()),
                request.getWhatsappContact(),
                request.getPopularTag(),
                Boolean.TRUE.equals(request.getLimitedOffer())
        );
    }

    private List<ImageDTO> buildImages(List<String> imageUrls, Integer primaryImageIndex) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return Collections.emptyList();
        }

        int resolvedPrimaryIndex = primaryImageIndex == null ? 0 : primaryImageIndex;
        if (resolvedPrimaryIndex < 0 || resolvedPrimaryIndex >= imageUrls.size()) {
            throw new BadRequestException("primaryImageIndex must be between 0 and " + (imageUrls.size() - 1));
        }

        List<ImageDTO> images = new ArrayList<>();
        for (int index = 0; index < imageUrls.size(); index++) {
            images.add(new ImageDTO(imageUrls.get(index), index == resolvedPrimaryIndex));
        }
        return images;
    }

    private List<String> parseStringList(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(value, new TypeReference<List<String>>() {});
        } catch (Exception ex) {
            throw new BadRequestException(fieldName + " must be a valid JSON string array");
        }
    }

    private List<ItineraryDayDTO> parseItinerary(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(value, new TypeReference<List<ItineraryDayDTO>>() {});
        } catch (Exception ex) {
            throw new BadRequestException("itineraryJson must be a valid JSON array");
        }
    }
}
