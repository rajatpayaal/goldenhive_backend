package com.goldenhive.backend.notification;

public interface INotification {
    
    /**
     * Send notification
     */
    void send(String recipient, String subject, String message);
    
    /**
     * Send notification with attachments (e.g., PDF)
     */
    void sendWithAttachment(String recipient, String subject, String message, String attachmentPath);
    
    /**
     * Get notification type
     */
    String getType();
}
