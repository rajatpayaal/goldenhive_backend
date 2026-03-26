package com.goldenhive.backend.helper;

import com.goldenhive.backend.dto.CreateActivityFormRequest;
import com.goldenhive.backend.dto.CreateActivityRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityFormDataHelper {

    public CreateActivityRequest toCreateActivityRequest(CreateActivityFormRequest request, List<String> imageUrls) {
        return new CreateActivityRequest(
                request.getName(),
                request.getType(),
                request.getPrice(),
                request.getDuration(),
                imageUrls,
                request.getLocation()
        );
    }
}
