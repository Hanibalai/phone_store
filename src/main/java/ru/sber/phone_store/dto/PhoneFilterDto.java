package ru.sber.phone_store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneFilterDto {
    private Long brandId;
    private Double minPrice;
    private Double maxPrice;
}
