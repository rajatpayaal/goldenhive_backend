package com.goldenhive.backend.controller;

import com.goldenhive.backend.dto.CreateReviewRequest;
import com.goldenhive.backend.dto.ReviewDTO;
import com.goldenhive.backend.iservice.IReviewService;
import com.goldenhive.backend.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "REVIEW APIs (2)", description = "Review endpoints")
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("/api/user/review")
    @Operation(summary = "[REVIEW] Create review")
    public ApiResponse<ReviewDTO> create(@Valid @RequestBody CreateReviewRequest request) {
        return ApiResponse.<ReviewDTO>builder().success(true).message("Review added").data(reviewService.createReview(request)).build();
    }

    @GetMapping("/api/user/reviews/{packageId}")
    @Operation(summary = "[REVIEW] Get package reviews")
    public ApiResponse<List<ReviewDTO>> reviews(@PathVariable String packageId) {
        return ApiResponse.<List<ReviewDTO>>builder().success(true).message("Reviews fetched").data(reviewService.getPackageReviews(packageId)).build();
    }
}


