package ru.sber.phone_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;
import ru.sber.phone_store.service.PhoneService;

import java.util.List;

/**
 * Контроллер для управления данными о телефонах.
 */
@RestController
@RequestMapping("/phones")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PhoneController {

    private final PhoneService phoneService;

    /**
     * Получает информацию о телефоне по его идентификатору.
     *
     * @param id Идентификатор телефона.
     * @return Объект PhoneDto с информацией о телефоне.
     */
    @GetMapping("/{id}")
    public PhoneDto getById(@PathVariable Long id) {
        log.info("GET request: Get phone by ID - id: {}", id);
        return phoneService.getById(id);
    }

    /**
     * Получает список всех доступных телефонов.
     *
     * @return Список объектов PhoneDto с информацией о телефонах.
     */
    @GetMapping
    public List<PhoneDto> getAll() {
        log.info("GET request: Get all phones");
        return phoneService.getAll();
    }

    /**
     * Получает список телефонов с учетом фильтра, переданного как атрибут через запрос.
     *
     * @param filter Объект {@link PhoneFilterDto} с параметрами фильтрации телефонов.
     * @return Список объектов {@link PhoneDto} с информацией о телефонах, соответствующих фильтру.
     */
    @GetMapping("/filter")
    public List<PhoneDto> getAllByFilter(@ModelAttribute PhoneFilterDto filter) {
        log.info("GET request: Get all phones with filter {}", filter);
        return phoneService.getAllByFilter(filter);
    }

    /**
     * Сохраняет информацию о новом телефоне.
     *
     * @param phoneDto Объект PhoneDto с информацией о телефоне для сохранения.
     * @return Объект PhoneDto с информацией о сохраненном телефоне.
     */
    @PostMapping
    public PhoneDto save(@RequestBody @Valid PhoneDto phoneDto) {
        log.info("POST request: Save phone - model: {}", phoneDto.getModel());
        return phoneService.save(phoneDto);
    }

    /**
     * Обновляет информацию о существующем телефоне по его идентификатору.
     *
     * @param id       Идентификатор телефона для обновления.
     * @param phoneDto Объект PhoneDto с информацией для обновления телефона.
     * @return Объект PhoneDto с обновленной информацией о телефоне.
     */
    @PutMapping("/{id}")
    public PhoneDto update(@PathVariable Long id, @RequestBody PhoneDto phoneDto) {
        log.info("PUT request: Update phone by ID - id: {}, model: {}", id, phoneDto.getModel());
        return phoneService.update(id, phoneDto);
    }

    /**
     * Удаляет телефон по его идентификатору.
     *
     * @param id Идентификатор телефона для удаления.
     */
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info("DELETE request: Remove phone by ID - id: {}", id);
        phoneService.remove(id);
    }
}
