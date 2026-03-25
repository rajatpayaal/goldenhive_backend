package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    
    /**
     * Find all carts for a user
     */
    List<Cart> findByUserId(String userId);
    
    /**
     * Find cart by user and package
     */
    Optional<Cart> findByUserIdAndPackageId(String userId, String packageId);
    
    /**
     * Find all carts for a package
     */
    List<Cart> findByPackageId(String packageId);
    
    /**
     * Delete all carts for a user
     */
    void deleteByUserId(String userId);
    
    /**
     * Delete cart by user and package
     */
    void deleteByUserIdAndPackageId(String userId, String packageId);
}
