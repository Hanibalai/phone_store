package ru.sber.phone_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.phone_store.dto.BrandDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.exception.EntityNotFoundException;
import ru.sber.phone_store.mapper.BrandMapper;
import ru.sber.phone_store.repository.BrandRepository;

import java.util.List;

/**
 * Сервис для управления информацией о брендах телефонов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    /**
     * Сохраняет информацию о новом бренде.
     *
     * @param brandDto Объект BrandDto с информацией о бренде для сохранения.
     * @return Объект BrandDto с информацией о сохраненном бренде.
     */
    @Transactional
    public BrandDto save(BrandDto brandDto) {
        Brand brand = brandMapper.toEntity(brandDto);
        brand = brandRepository.save(brand);
        log.info("Brand with id {} saved successfully", brand.getId());
        return brandMapper.toDto(brand);
    }

    /**
     * Получает информацию о бренде по его идентификатору.
     *
     * @param id Идентификатор бренда.
     * @return Объект BrandDto с информацией о бренде.
     * @throws EntityNotFoundException если бренд с указанным идентификатором не найден.
     */
    @Transactional(readOnly = true)
    public BrandDto getById(Long id) {
        Brand brand = findBrandById(id);
        return brandMapper.toDto(brand);
    }

    /**
     * Получает список всех доступных брендов.
     *
     * @return Список объектов BrandDto с информацией о брендах.
     */
    @Transactional(readOnly = true)
    public List<BrandDto> getAll() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toDto)
                .toList();
    }

    /**
     * Удаляет бренд по его идентификатору.
     *
     * @param id Идентификатор бренда для удаления.
     */
    @Transactional
    public void remove(Long id) {
        brandRepository.deleteById(id);
        log.info("Brand with id {} removed successfully", id);
    }

    protected Brand findBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + id + " not found"));
    }
}
