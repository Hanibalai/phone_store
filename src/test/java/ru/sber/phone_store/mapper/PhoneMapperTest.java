package ru.sber.phone_store.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.entity.Phone;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhoneMapperTest {
    @Spy
    private PhoneMapperImpl phoneMapper;
    private Phone phone;
    private PhoneDto phoneDto;

    @BeforeEach
    void setUp() {
        LocalDateTime time = LocalDateTime.of(2023, 1, 1, 0, 0, 0);

        phone = Phone.builder()
                .id(1L)
                .brand(Brand.builder().id(1L).build())
                .model("model")
                .color("color")
                .price(100.0)
                .createdAt(time)
                .updatedAt(time)
                .build();

        phoneDto = PhoneDto.builder()
                .id(1L)
                .brandId(1L)
                .model("model")
                .color("color")
                .price(100.0)
                .createdAt(time)
                .updatedAt(time)
                .build();
    }

    @Test
    void testToDto() {
        PhoneDto expected = phoneDto;
        PhoneDto actual = phoneMapper.toDto(phone);
        assertEquals(expected, actual);
    }

    @Test
    void testToEntity() {
        Phone expected = phone;
        expected.setBrand(null);
        Phone actual = phoneMapper.toEntity(phoneDto);
        assertEquals(expected, actual);
    }

    @Test
    void testUpdate() {
        PhoneDto phoneDtoToUpdate = PhoneDto.builder()
                .model("new model")
                .color("new color")
                .price(10000.0)
                .build();

        phoneMapper.update(phone, phoneDtoToUpdate);

        Phone expected = Phone.builder()
                .id(1L)
                .brand(Brand.builder().id(1L).build())
                .model("new model")
                .color("new color")
                .price(10000.0)
                .createdAt(phone.getCreatedAt())
                .updatedAt(phone.getUpdatedAt())
                .build();

        assertEquals(expected, phone);
    }
}