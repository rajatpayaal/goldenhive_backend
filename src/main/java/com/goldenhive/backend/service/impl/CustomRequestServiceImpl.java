package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.CreateCustomRequestDTO;
import com.goldenhive.backend.dto.CustomRequestDTO;
import com.goldenhive.backend.entity.CustomRequest;
import com.goldenhive.backend.enums.CustomRequestStatus;
import com.goldenhive.backend.exception.NotFoundException;
import com.goldenhive.backend.iservice.ICustomRequestService;
import com.goldenhive.backend.repository.CustomRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomRequestServiceImpl implements ICustomRequestService {
    private final CustomRequestRepository customRequestRepository;

    @Override
    public CustomRequestDTO createRequest(CreateCustomRequestDTO request) {
        CustomRequest customRequest = new CustomRequest();
        customRequest.setUserId(request.getUserId());
        customRequest.setDestinations(request.getDestinations());
        customRequest.setBudget(request.getBudget());
        customRequest.setTravelers(request.getTravelers());
        customRequest.setPreferences(request.getPreferences());
        customRequest.setStatus(CustomRequestStatus.PENDING);
        customRequest.setCreatedAt(LocalDateTime.now());
        customRequest.setUpdatedAt(LocalDateTime.now());
        return toDto(customRequestRepository.save(customRequest));
    }

    @Override
    public List<CustomRequestDTO> getMyRequests(String userId) {
        return customRequestRepository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public List<CustomRequestDTO> getAllRequests() {
        return customRequestRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public CustomRequestDTO getById(String requestId) {
        return toDto(findRequest(requestId));
    }

    @Override
    public CustomRequestDTO approve(String requestId) {
        CustomRequest request = findRequest(requestId);
        request.setStatus(CustomRequestStatus.APPROVED);
        request.setUpdatedAt(LocalDateTime.now());
        return toDto(customRequestRepository.save(request));
    }

    @Override
    public CustomRequestDTO reject(String requestId) {
        CustomRequest request = findRequest(requestId);
        request.setStatus(CustomRequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        return toDto(customRequestRepository.save(request));
    }

    private CustomRequest findRequest(String requestId) {
        return customRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Custom request not found with ID: " + requestId));
    }

    private CustomRequestDTO toDto(CustomRequest request) {
        return new CustomRequestDTO(
                request.getRequestId(),
                request.getUserId(),
                request.getDestinations(),
                request.getBudget(),
                request.getTravelers(),
                request.getPreferences(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getUpdatedAt()
        );
    }
}
