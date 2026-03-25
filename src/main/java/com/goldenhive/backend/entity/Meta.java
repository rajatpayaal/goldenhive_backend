package com.goldenhive.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.goldenhive.backend.enums.PackageStatus;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PackageStatus status;
}
