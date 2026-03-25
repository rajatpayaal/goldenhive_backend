package com.goldenhive.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard API Response Format")
public class ApiResponse<T> {
    
    @Schema(description = "Success flag", example = "true")
    private boolean success;
    
    @Schema(description = "Response message", example = "Operation successful")
    private String message;
    
    @Schema(description = "Response data")
    private T data;
}
