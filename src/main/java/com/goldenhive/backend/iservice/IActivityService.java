package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.ActivityDTO;
import com.goldenhive.backend.dto.CreateActivityFormRequest;
import com.goldenhive.backend.dto.CreateActivityRequest;
import java.util.List;
import java.util.Optional;

public interface IActivityService {
    
    /**
     * Create a new activity
     */
    ActivityDTO createActivity(CreateActivityRequest request);

    /**
     * Create a new activity with multipart/form-data
     */
    ActivityDTO createActivity(CreateActivityFormRequest request);
    
    /**
     * Get activity by ID
     */
    Optional<ActivityDTO> getActivityById(String activityId);
    
    /**
     * Get all activities
     */
    List<ActivityDTO> getAllActivities();
    
    /**
     * Get activities by type
     */
    List<ActivityDTO> getActivitiesByType(String type);
    
    /**
     * Get activities by location
     */
    List<ActivityDTO> getActivitiesByLocation(String location);
    
    /**
     * Search activities by name
     */
    List<ActivityDTO> searchActivities(String name);
    
    /**
     * Get activities within price range
     */
    List<ActivityDTO> getActivitiesByPriceRange(double minPrice, double maxPrice);
    
    /**
     * Update activity
     */
    ActivityDTO updateActivity(String activityId, CreateActivityRequest request);

    /**
     * Update activity with multipart/form-data
     */
    ActivityDTO updateActivity(String activityId, CreateActivityFormRequest request);
    
    /**
     * Delete activity
     */
    void deleteActivity(String activityId);
}
