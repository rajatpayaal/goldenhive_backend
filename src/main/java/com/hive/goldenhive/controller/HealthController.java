package com.hive.goldenhive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new HealthResponse(
            "ok",
            "Golden Hive Backend is running",
            System.currentTimeMillis()
        ));
    }

    static class HealthResponse {
        public String status;
        public String message;
        public long timestamp;

        public HealthResponse(String status, String message, long timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}
