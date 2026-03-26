package com.goldenhive.backend.iservice;

import com.goldenhive.backend.dto.NotificationDTO;

import java.util.List;

public interface INotificationQueryService {
    List<NotificationDTO> getAdminNotifications();
}
