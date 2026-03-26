package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.CreateReviewRequest;
import com.goldenhive.backend.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
    ReviewDTO createReview(CreateReviewRequest request);
    List<ReviewDTO> getPackageReviews(String packageId);
}
