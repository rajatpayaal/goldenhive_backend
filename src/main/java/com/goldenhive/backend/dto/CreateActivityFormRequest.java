package com.goldenhive.backend.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create Activity Request using multipart/form-data")
public class CreateActivityFormRequest {

    @NotBlank(message = "Activity name is required")
    @Schema(description = "Activity name", example = "Scuba Diving")
    private String name;

    @NotBlank(message = "Activity type is required")
    @Schema(description = "Activity type", example = "Water Sport")
    private String type;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Price", example = "5000")
    private Double price;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Duration in hours", example = "3")
    private Integer duration;

    @ArraySchema(schema = @Schema(type = "string", format = "binary", description = "Upload image/video/file"))
    private List<MultipartFile> images;

    @NotBlank(message = "Location is required")
    @Schema(description = "Location", example = "Maldives")
    private String location;
}
