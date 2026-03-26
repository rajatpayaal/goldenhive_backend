package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.CreateReviewRequest;
import com.goldenhive.backend.dto.ReviewDTO;
import com.goldenhive.backend.entity.Review;
import com.goldenhive.backend.iservice.IReviewService;
import com.goldenhive.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewDTO createReview(CreateReviewRequest request) {
        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setPackageId(request.getPackageId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        return toDto(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDTO> getPackageReviews(String packageId) {
        return reviewRepository.findByPackageId(packageId).stream().map(this::toDto).toList();
    }

    private ReviewDTO toDto(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getUserId(),
                review.getPackageId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
