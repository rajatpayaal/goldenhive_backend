package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.ActivityDTO;
import com.goldenhive.backend.dto.CreateActivityFormRequest;
import com.goldenhive.backend.dto.CreateActivityRequest;
import com.goldenhive.backend.entity.Activity;
import com.goldenhive.backend.helper.ActivityFormDataHelper;
import com.goldenhive.backend.helper.S3FileUploadHelper;
import com.goldenhive.backend.iservice.IActivityService;
import com.goldenhive.backend.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl implements IActivityService {
    
    private final ActivityRepository activityRepository;
    private final S3FileUploadHelper s3FileUploadHelper;
    private final ActivityFormDataHelper activityFormDataHelper;
    
    @Override
    public ActivityDTO createActivity(CreateActivityRequest request) {
        log.info("Creating activity: {}", request.getName());
        
        Activity activity = new Activity();
        activity.setName(request.getName());
        activity.setType(request.getType());
        activity.setPrice(request.getPrice());
        activity.setDuration(request.getDuration());
        activity.setImages(request.getImages());
        activity.setLocation(request.getLocation());
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());
        
        Activity savedActivity = activityRepository.save(activity);
        log.info("Activity created with ID: {}", savedActivity.getActivityId());
        
        return mapToDTO(savedActivity);
    }

    @Override
    public ActivityDTO createActivity(CreateActivityFormRequest request) {
        List<String> imageUrls = s3FileUploadHelper.uploadFiles(request.getImages(), "activities");
        CreateActivityRequest activityRequest = activityFormDataHelper.toCreateActivityRequest(request, imageUrls);
        return createActivity(activityRequest);
    }
    
    @Override
    public Optional<ActivityDTO> getActivityById(String activityId) {
        log.info("Fetching activity with ID: {}", activityId);
        return activityRepository.findById(activityId).map(this::mapToDTO);
    }
    
    @Override
    public List<ActivityDTO> getAllActivities() {
        log.info("Fetching all activities");
        return activityRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ActivityDTO> getActivitiesByType(String type) {
        log.info("Fetching activities by type: {}", type);
        return activityRepository.findByType(type)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ActivityDTO> getActivitiesByLocation(String location) {
        log.info("Fetching activities by location: {}", location);
        return activityRepository.findByLocation(location)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ActivityDTO> searchActivities(String name) {
        log.info("Searching activities with name: {}", name);
        return activityRepository.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ActivityDTO> getActivitiesByPriceRange(double minPrice, double maxPrice) {
        log.info("Fetching activities in price range: {} - {}", minPrice, maxPrice);
        return activityRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ActivityDTO updateActivity(String activityId, CreateActivityRequest request) {
        log.info("Updating activity with ID: {}", activityId);
        
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + activityId));
        
        activity.setName(request.getName());
        activity.setType(request.getType());
        activity.setPrice(request.getPrice());
        activity.setDuration(request.getDuration());
        activity.setImages(request.getImages());
        activity.setLocation(request.getLocation());
        activity.setUpdatedAt(LocalDateTime.now());
        
        Activity updatedActivity = activityRepository.save(activity);
        log.info("Activity updated with ID: {}", activityId);
        
        return mapToDTO(updatedActivity);
    }

    @Override
    public ActivityDTO updateActivity(String activityId, CreateActivityFormRequest request) {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + activityId));

        List<String> imageUrls = s3FileUploadHelper.uploadFiles(request.getImages(), "activities");
        CreateActivityRequest activityRequest = activityFormDataHelper.toCreateActivityRequest(request, imageUrls);
        if (imageUrls.isEmpty()) {
            activityRequest.setImages(existingActivity.getImages());
        }
        return updateActivity(activityId, activityRequest);
    }
    
    @Override
    public void deleteActivity(String activityId) {
        log.info("Deleting activity with ID: {}", activityId);
        
        if (!activityRepository.existsById(activityId)) {
            throw new RuntimeException("Activity not found with ID: " + activityId);
        }
        
        activityRepository.deleteById(activityId);
        log.info("Activity deleted with ID: {}", activityId);
    }
    
    /**
     * Convert Activity entity to ActivityDTO
     */
    private ActivityDTO mapToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getActivityId(),
                activity.getName(),
                activity.getType(),
                activity.getPrice(),
                activity.getDuration(),
                activity.getImages(),
                activity.getLocation(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }
}
