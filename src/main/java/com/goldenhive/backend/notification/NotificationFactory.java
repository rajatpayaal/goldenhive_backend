package com.goldenhive.backend.notification;

import com.goldenhive.backend.notification.impl.EmailNotification;
import com.goldenhive.backend.notification.impl.WhatsAppNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Factory;

@Factory
@RequiredArgsConstructor
@Slf4j
public class NotificationFactory {
    
    private final EmailNotification emailNotification;
    private final WhatsAppNotification whatsAppNotification;
    
    /**
     * Enum for notification types
     */
    public enum NotificationType {
        EMAIL,
        WHATSAPP
    }
    
    /**
     * Factory method to create notification instance
     */
    public INotification createNotification(NotificationType type) {
        log.info("Creating notification of type: {}", type);
        
        return switch (type) {
            case EMAIL -> emailNotification;
            case WHATSAPP -> whatsAppNotification;
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
    
    /**
     * Factory method with string type
     */
    public INotification createNotification(String type) {
        log.info("Creating notification with string type: {}", type);
        
        return switch (type.toUpperCase()) {
            case "EMAIL" -> emailNotification;
            case "WHATSAPP" -> whatsAppNotification;
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
    
    /**
     * Send notification with factory
     */
    public void sendNotification(NotificationType type, String recipient, String subject, String message) {
        log.info("Sending notification via {} to {}", type, recipient);
        INotification notification = createNotification(type);
        notification.send(recipient, subject, message);
    }
    
    /**
     * Send notification with attachment
     */
    public void sendNotificationWithAttachment(NotificationType type, String recipient, 
                                               String subject, String message, String attachmentPath) {
        log.info("Sending notification with attachment via {} to {}", type, recipient);
        INotification notification = createNotification(type);
        notification.sendWithAttachment(recipient, subject, message, attachmentPath);
    }
}
