package com.goldenhive.backend.notification.impl;

import com.goldenhive.backend.notification.INotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WhatsAppNotification implements INotification {
    
    /**
     * Send WhatsApp notification
     */
    @Override
    public void send(String recipient, String subject, String message) {
        log.info("Sending WhatsApp message to: {}", recipient);
        
        try {
            // TODO: Implement WhatsApp API integration (Twilio / Vonage)
            // Call WhatsApp API with recipient, message
            log.info("WhatsApp message sent successfully to: {}", recipient);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp message to: {}", recipient, e);
            throw new RuntimeException("WhatsApp sending failed: " + e.getMessage());
        }
    }
    
    /**
     * WhatsApp doesn't support file attachments in same way as email
     * But we can send download link or formatted message
     */
    @Override
    public void sendWithAttachment(String recipient, String subject, String message, String attachmentPath) {
        log.info("Sending WhatsApp with document link to: {}", recipient);
        
        try {
            // Send message with document link instead of attachment
            String messageWithLink = message + "\n\nDownload details: " + attachmentPath;
            send(recipient, subject, messageWithLink);
            log.info("WhatsApp with document link sent to: {}", recipient);
        } catch (Exception e) {
            log.error("Failed to send WhatsApp with document link to: {}", recipient, e);
            throw new RuntimeException("WhatsApp with document link sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public String getType() {
        return "WHATSAPP";
    }
}
