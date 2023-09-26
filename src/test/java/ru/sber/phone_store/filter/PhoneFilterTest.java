package ru.sber.phone_store.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneFilterTest {
    private List<PhoneFilter> phoneFilters;
    private List<PhoneDto> phones;

    @BeforeEach
    void setUp() {
        phoneFilters = List.of(
                new PhoneBrandFilter(),
                new PhoneMaxPriceFilter(),
                new PhoneMinPriceFilter()
        );

        phones = new ArrayList<>();
        phones.add(PhoneDto.builder()
                .brandId(1L)
                .price(100.0)
                .build());
        phones.add(PhoneDto.builder()
                .brandId(1L)
                .price(200.0)
                .build());
        phones.add(PhoneDto.builder()
                .brandId(2L)
                .price(200.0)
                .build());
        phones.add(PhoneDto.builder()
                .brandId(1L)
                .price(300.0)
                .build());
    }

    @Test
    void testIsApplicable_shouldReturnPhoneBrandFilter() {
        PhoneFilterDto filter = PhoneFilterDto.builder()
                .brandId(1L)
                .build();

        phoneFilters.stream()
                .filter(f -> f.isApplicable(filter))
                .findFirst()
                .ifPresentOrElse(
                        f -> assertTrue(f instanceof PhoneBrandFilter),
                        Assertions::fail);
    }

    @Test
    void testIsApplicable_shouldReturnPhoneMaxPriceFilter() {
        PhoneFilterDto filter = PhoneFilterDto.builder()
                .maxPrice(250.0)
                .build();

        phoneFilters.stream()
                .filter(f -> f.isApplicable(filter))
                .findFirst()
                .ifPresentOrElse(
                        f -> assertTrue(f instanceof PhoneMaxPriceFilter),
                        Assertions::fail);
    }

    @Test
    void testIsApplicable_shouldReturnPhoneMinPriceFilter() {
        PhoneFilterDto filter = PhoneFilterDto.builder()
                .minPrice(250.0)
                .build();

        phoneFilters.stream()
                .filter(f -> f.isApplicable(filter))
                .findFirst()
                .ifPresentOrElse(
                        f -> assertTrue(f instanceof PhoneMinPriceFilter),
                        Assertions::fail);
    }

    @Test
    void testApply_shouldFindOnePhone() {
        PhoneFilterDto filter = PhoneFilterDto.builder()
                .brandId(1L)
                .maxPrice(250.0)
                .minPrice(150.0)
                .build();

        phoneFilters.stream()
                .filter(f -> f.isApplicable(filter))
                .forEach(f -> f.apply(phones, filter));

        assertAll(() -> {
            assertEquals(1, phones.size());
            assertEquals(200.0, phones.get(0).getPrice());
        });
    }
}