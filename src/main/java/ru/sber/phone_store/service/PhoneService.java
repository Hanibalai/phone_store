package ru.sber.phone_store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.entity.Phone;
import ru.sber.phone_store.exception.DataValidationException;
import ru.sber.phone_store.exception.EntityNotFoundException;
import ru.sber.phone_store.filter.PhoneFilter;
import ru.sber.phone_store.mapper.PhoneMapper;
import ru.sber.phone_store.repository.PhoneRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Простой CRUD сервис для работы с сущностью Phone.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;
    private final BrandService brandService;
    private final List<PhoneFilter> phoneFilters;

    /**
     * Получает информацию о телефоне по его ID.
     *
     * @param id Идентификатор телефона.
     * @return Объект PhoneDto с информацией о телефоне.
     * @throws EntityNotFoundException если телефон с указанным идентификатором не найден.
     */
    @Transactional(readOnly = true)
    public PhoneDto getById(Long id) {
        Phone phone = findPhoneById(id);
        return phoneMapper.toDto(phone);
    }

    /**
     * Получает список всех доступных телефонов.
     *
     * @return Список объектов PhoneDto с информацией о телефонах.
     */
    @Transactional(readOnly = true)
    public List<PhoneDto> getAll() {
        return phoneRepository.findAll().stream()
                .map(phoneMapper::toDto)
                .toList();
    }

    /**
     * Получает список телефонов с учетом фильтра, определенного в объекте {@link PhoneFilterDto}.
     *
     * @param filter Объект {@link PhoneFilterDto}, содержащий параметры фильтрации.
     * @return Список объектов {@link PhoneDto} с информацией о телефонах, соответствующих фильтру.
     */
    @Transactional(readOnly = true)
    public List<PhoneDto> getAllByFilter(PhoneFilterDto filter) {
        List<PhoneDto> phones = phoneRepository.findAll().stream()
                .map(phoneMapper::toDto)
                .collect(Collectors.toList());

        phoneFilters.stream()
                .filter(phoneFilter -> phoneFilter.isApplicable(filter))
                .forEach(phoneFilter -> phoneFilter.apply(phones, filter));

        return phones;
    }

    /**
     * Сохраняет информацию о новом телефоне.
     *
     * @param phoneDto Объект PhoneDto с информацией о телефоне для сохранения.
     * @return Объект PhoneDto с информацией о сохраненном телефоне.
     * @throws EntityNotFoundException если бренд с указанным идентификатором не найден.
     */
    @Transactional
    public PhoneDto save(PhoneDto phoneDto) {
        Phone phone = phoneMapper.toEntity(phoneDto);
        Brand brand = brandService.findBrandById(phoneDto.getBrandId());
        phone.setBrand(brand);
        phone = phoneRepository.save(phone);

        log.info("Phone with id {} saved successfully", phone.getId());
        return phoneMapper.toDto(phone);
    }

    /**
     * Обновляет информацию о существующем телефоне по его идентификатору.
     *
     * @param id       Идентификатор телефона для обновления.
     * @param phoneDto Объект PhoneDto с информацией для обновления телефона.
     * @return Объект PhoneDto с обновленной информацией о телефоне.
     * @throws EntityNotFoundException    если телефон с указанным идентификатором не найден.
     * @throws DataValidationException     если цена (price) меньше или равна 0.
     */
    @Transactional
    public PhoneDto update(Long id, PhoneDto phoneDto) {
        Phone phone = findPhoneById(id);
        if (phoneDto.getPrice() <= 0) {
            throw new DataValidationException("Price must be greater than 0");
        }
        phoneMapper.update(phone, phoneDto);

        log.info("Phone with id {} updated successfully", id);
        return phoneMapper.toDto(phone);
    }

    /**
     * Удаляет телефон по его идентификатору.
     *
     * @param id Идентификатор телефона для удаления.
     */
    @Transactional
    public void remove(Long id) {
        phoneRepository.deleteById(id);
        log.info("Phone with id {} removed successfully", id);
    }

    private Phone findPhoneById(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found"));
    }
}
