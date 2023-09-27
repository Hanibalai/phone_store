package ru.sber.phone_store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.sber.phone_store.dto.BrandDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.exception.EntityNotFoundException;
import ru.sber.phone_store.mapper.BrandMapper;
import ru.sber.phone_store.repository.BrandRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BrandServiceTest {
    @InjectMocks
    private BrandService brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandMapper brandMapper;
    private Brand brand;
    private BrandDto brandDto;

    @BeforeEach
    void setUp() {
        brand = Brand.builder().id(1L).build();
        brandDto = BrandDto.builder().id(1L).build();

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.findAll()).thenReturn(List.of(brand));
        when(brandRepository.save(brand)).thenReturn(brand);
        when(brandMapper.toDto(brand)).thenReturn(brandDto);
        when(brandMapper.toEntity(brandDto)).thenReturn(brand);
    }

    @Test
    void testSave_shouldInvokeBrandMapperToEntity() {
        brandService.save(brandDto);
        verify(brandMapper).toEntity(brandDto);
    }

    @Test
    void testSave_shouldInvokeBrandRepository() {
        brandService.save(brandDto);
        verify(brandRepository).save(brand);
    }

    @Test
    void testSave_shouldInvokeBrandMapperToDto() {
        brandService.save(brandDto);
        verify(brandMapper).toDto(brand);
    }

    @Test
    void testGetById_shouldInvokeBrandRepository() {
        brandService.getById(1L);
        verify(brandRepository).findById(1L);
    }

    @Test
    void testGetById_shouldInvokeBrandMapperToDto() {
        brandService.getById(1L);
        verify(brandMapper).toDto(brand);
    }

    @Test
    void testGetAll_shouldInvokeRepository() {
        brandService.getAll();
        verify(brandRepository).findAll();
    }

    @Test
    void testGetAll_shouldInvokeMapperToDto() {
        brandService.getAll();
        verify(brandMapper).toDto(brand);
    }

    @Test
    void testRemove_shouldInvokeRepository() {
        brandService.remove(1L);
        verify(brandRepository).deleteById(1L);
    }

    @Test
    void testFindBrandById_shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
                () -> brandService.findBrandById(2L),
                "Brand with id 2 not found");
    }

    @Test
    void testFindBrandById_shouldNothrowAnyException() {
        assertDoesNotThrow(() -> brandService.findBrandById(1L));
    }

    @Test
    void testFindBrandById_shouldInvokeBrandRepository() {
        brandService.findBrandById(1L);
        verify(brandRepository).findById(1L);
    }
}