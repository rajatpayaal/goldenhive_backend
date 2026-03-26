package com.goldenhive.backend.service.impl;

import com.goldenhive.backend.dto.DashboardStatsDTO;
import com.goldenhive.backend.enums.CustomRequestStatus;
import com.goldenhive.backend.iservice.IDashboardService;
import com.goldenhive.backend.repository.BookingRepository;
import com.goldenhive.backend.repository.CustomRequestRepository;
import com.goldenhive.backend.repository.PackageRepository;
import com.goldenhive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {
    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;
    private final CustomRequestRepository customRequestRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardStatsDTO getStats() {
        long pendingRequests = customRequestRepository.findByStatus(CustomRequestStatus.PENDING).size();
        return new DashboardStatsDTO(
                packageRepository.count(),
                bookingRepository.count(),
                pendingRequests,
                userRepository.count()
        );
    }
}
