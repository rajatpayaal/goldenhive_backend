package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.CreateCustomRequestDTO;
import com.goldenhive.backend.dto.CustomRequestDTO;

import java.util.List;

public interface ICustomRequestService {
    CustomRequestDTO createRequest(CreateCustomRequestDTO request);
    List<CustomRequestDTO> getMyRequests(String userId);
    List<CustomRequestDTO> getAllRequests();
    CustomRequestDTO getById(String requestId);
    CustomRequestDTO approve(String requestId);
    CustomRequestDTO reject(String requestId);
}
