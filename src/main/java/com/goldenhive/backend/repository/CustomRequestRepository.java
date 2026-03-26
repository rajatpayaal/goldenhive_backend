package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.CustomRequest;
import com.goldenhive.backend.enums.CustomRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRequestRepository extends MongoRepository<CustomRequest, String> {
    
    /**
     * Find all custom requests for a user
     */
    List<CustomRequest> findByUserId(String userId);
    
    /**
     * Find custom requests by status
     */
    List<CustomRequest> findByStatus(CustomRequestStatus status);
    
    /**
     * Find custom requests by user and status
     */
    List<CustomRequest> findByUserIdAndStatus(String userId, CustomRequestStatus status);
    
    /**
     * Find custom requests by status with pagination
     */
    Page<CustomRequest> findByStatus(CustomRequestStatus status, Pageable pageable);
    
    /**
     * Find all pending custom requests
     */
    List<CustomRequest> findByStatusOrderByCreatedAtDesc(CustomRequestStatus status);

    List<CustomRequest> findAllByOrderByCreatedAtDesc();
}
