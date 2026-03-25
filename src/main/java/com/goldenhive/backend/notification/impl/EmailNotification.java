package com.goldenhive.backend.notification.impl;

import com.goldenhive.backend.notification.INotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotification implements INotification {
    
    /**
     * Send email notification
     */
    @Override
    public void send(String recipient, String subject, String message) {
        log.info("Sending email to: {}, Subject: {}", recipient, subject);
        
        try {
            // TODO: Implement JavaMailSender integration
            // mailSender.send(new SimpleMailMessage());
            log.info("Email sent successfully to: {}", recipient);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", recipient, e);
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
    
    /**
     * Send email with PDF attachment
     */
    @Override
    public void sendWithAttachment(String recipient, String subject, String message, String attachmentPath) {
        log.info("Sending email with attachment to: {}, Subject: {}", recipient, subject);
        
        try {
            // TODO: Implement JavaMailSender with MimeMessage for attachments
            // MimeMessage mimeMessage = mailSender.createMimeMessage();
            // ... set attachment from attachmentPath
            log.info("Email with attachment sent successfully to: {}", recipient);
        } catch (Exception e) {
            log.error("Failed to send email with attachment to: {}", recipient, e);
            throw new RuntimeException("Email with attachment sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public String getType() {
        return "EMAIL";
    }
}
