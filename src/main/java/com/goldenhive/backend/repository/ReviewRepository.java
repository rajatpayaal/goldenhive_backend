package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByPackageId(String packageId);
    List<Review> findByUserId(String userId);
}
