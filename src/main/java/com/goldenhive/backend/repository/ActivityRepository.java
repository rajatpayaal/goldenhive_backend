package com.goldenhive.backend.repository;

import com.goldenhive.backend.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    
    /**
     * Find activities by type
     */
    List<Activity> findByType(String type);
    
    /**
     * Find activities by location
     */
    List<Activity> findByLocation(String location);
    
    /**
     * Search activities by name
     */
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Activity> searchByName(String name);
    
    /**
     * Find activities with pagination
     */
    Page<Activity> findAll(Pageable pageable);
    
    /**
     * Find activities by type with pagination
     */
    Page<Activity> findByType(String type, Pageable pageable);
    
    /**
     * Find activities by location with pagination
     */
    Page<Activity> findByLocation(String location, Pageable pageable);
    
    /**
     * Find activities within price range
     */
    @Query("{'price': {$gte: ?0, $lte: ?1}}")
    List<Activity> findByPriceRange(double minPrice, double maxPrice);
}
