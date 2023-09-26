package ru.sber.phone_store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Brand is required")
    private Long brandId;

    @NotBlank(message = "Model is required")
    private String model;

    private String color;

    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
