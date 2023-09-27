package ru.sber.phone_store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.entity.Phone;
import ru.sber.phone_store.exception.DataValidationException;
import ru.sber.phone_store.filter.PhoneBrandFilter;
import ru.sber.phone_store.filter.PhoneFilter;
import ru.sber.phone_store.filter.PhoneMaxPriceFilter;
import ru.sber.phone_store.filter.PhoneMinPriceFilter;
import ru.sber.phone_store.mapper.PhoneMapper;
import ru.sber.phone_store.repository.PhoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PhoneServiceTest {
    @InjectMocks
    private PhoneService phoneService;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private BrandService brandService;
    @Mock
    private List<PhoneFilter> filters;
    @Mock
    private PhoneMapper phoneMapper;
    private Phone phone;
    private PhoneDto phoneDto;
    private PhoneFilterDto filterDto;

    @BeforeEach
    void setUp() {
        Brand brand = Brand.builder().id(1L).build();
        phone = Phone.builder()
                .id(1L)
                .brand(brand)
                .price(100.0)
                .build();
        phoneDto = PhoneDto.builder()
                .id(1L)
                .brandId(1L)
                .price(100.0)
                .build();
        filterDto = PhoneFilterDto.builder().brandId(1L).build();

        when(phoneRepository.findById(1L)).thenReturn(Optional.of(phone));
        when(phoneRepository.findAll()).thenReturn(List.of(phone));
        when(phoneRepository.save(phone)).thenReturn(phone);
        when(phoneMapper.toDto(phone)).thenReturn(phoneDto);
        when(phoneMapper.toEntity(phoneDto)).thenReturn(phone);
        when(brandService.findBrandById(1L)).thenReturn(brand);
    }

    @Test
    void testGetById_shouldInvokeRepositoryFindById() {
        phoneService.getById(1L);
        verify(phoneRepository).findById(1L);
    }

    @Test
    void testGetById_shouldInvokePhoneMapperToEntity() {
        phoneService.getById(1L);
        verify(phoneMapper).toDto(phone);
    }

    @Test
    void testGetAll_shouldInvokeRepositoryFindAll() {
        phoneService.getAll();
        verify(phoneRepository).findAll();
    }

    @Test
    void testGetAll_shouldInvokeMapperToDto() {
        phoneService.getAll();
        verify(phoneMapper).toDto(phone);
    }

    @Test
    void testGetAllByFilter_shouldInvokeRepositoryFindAll() {
        phoneService.getAllByFilter(filterDto);
        verify(phoneRepository).findAll();
    }

    @Test
    void testGetAllByFilter_shouldInvokeMapperToDto() {
        phoneService.getAllByFilter(filterDto);
        verify(phoneMapper).toDto(phone);
    }

    @Test
    void testGetAllByFilter_shouldInvokeFiltersMethods() {
        when(filters.stream()).thenReturn(Stream.of(
                mock(PhoneBrandFilter.class),
                mock(PhoneMaxPriceFilter.class),
                mock(PhoneMinPriceFilter.class)));

        phoneService.getAllByFilter(filterDto);
        filters.forEach(filter -> verify(filter).isApplicable(filterDto));
        filters.forEach(filter -> verify(filter).apply(List.of(phoneDto), filterDto));
    }

    @Test
    void testSave_shouldInvokeMapperToEntity() {
        phoneService.save(phoneDto);
        verify(phoneMapper).toEntity(phoneDto);
    }

    @Test
    void testSave_shouldInvokeBrandServiceFindBrandById() {
        phoneService.save(phoneDto);
        verify(brandService).findBrandById(1L);
    }

    @Test
    void testSave_shouldInvokePhoneRepositorySave() {
        phoneService.save(phoneDto);
        verify(phoneRepository).save(phone);
    }

    @Test
    void testSave_shouldInvokePhoneMapperToDto() {
        phoneService.save(phoneDto);
        verify(phoneMapper).toDto(phone);
    }

    @Test
    void testUpdate_shouldInvokeMapperToEntity() {
        phoneService.update(1L, phoneDto);
        verify(phoneRepository).findById(1L);
    }

    @Test
    void testUpdate_shouldInvokeMapperUpdate() {
        phoneService.update(1L, phoneDto);
        verify(phoneMapper).update(phone, phoneDto);
    }

    @Test
    void testUpdate_shouldInvokeMapperToDto() {
        phoneService.update(1L, phoneDto);
        verify(phoneMapper).toDto(phone);
    }

    @Test
    void testUpdate_shouldThrowDataValidationException() {
        phoneDto.setPrice(0.0);
        assertThrows(DataValidationException.class,
                () -> phoneService.update(1L, phoneDto),
                "Price must be greater than 0");
    }

    @Test
    void testRemove_shouldInvokeRepositoryDeleteById() {
        phoneService.remove(1L);
        verify(phoneRepository).deleteById(1L);
    }
}