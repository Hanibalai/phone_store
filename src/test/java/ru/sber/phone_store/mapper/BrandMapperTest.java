package ru.sber.phone_store.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.phone_store.dto.BrandDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.entity.Phone;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BrandMapperTest {
    @Spy
    private BrandMapperImpl brandMapper;
    private Brand brand;
    private BrandDto brandDto;

    @BeforeEach
    void setUp() {
        List<Phone> phones = List.of(
                Phone.builder().id(1L).build(),
                Phone.builder().id(2L).build()
        );

        brand = Brand.builder()
                .id(1L)
                .name("brand")
                .phones(phones)
                .build();

        brandDto = BrandDto.builder()
                .id(1L)
                .name("brand")
                .phoneIds(List.of(1L, 2L))
                .build();
    }

    @Test
    void testToDto() {
        BrandDto expected = brandDto;
        BrandDto actual = brandMapper.toDto(brand);
        assertEquals(expected, actual);
    }

    @Test
    void testToEntity() {
        Brand expected = brand;
        expected.setPhones(null);
        Brand actual = brandMapper.toEntity(brandDto);
        assertEquals(expected, actual);
    }
}