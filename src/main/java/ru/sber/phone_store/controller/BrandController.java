package ru.sber.phone_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.phone_store.dto.BrandDto;
import ru.sber.phone_store.service.BrandService;

import java.util.List;

/**
 * Контроллер для управления данными о брендах.
 */
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BrandController {

    private final BrandService brandService;

    /**
     * Получает информацию о бренде по его идентификатору.
     *
     * @param id Идентификатор бренда.
     * @return Объект BrandDto с информацией о бренде.
     */
    @GetMapping("/{id}")
    public BrandDto getById(@PathVariable Long id) {
        log.info("GET request: Get brand by ID - id: {}", id);
        return brandService.getById(id);
    }

    /**
     * Получает список всех доступных брендов.
     *
     * @return Список объектов BrandDto с информацией о брендах.
     */
    @GetMapping
    public List<BrandDto> getAll() {
        log.info("GET request: Get all brands");
        return brandService.getAll();
    }

    /**
     * Сохраняет информацию о новом бренде.
     *
     * @param brandDto Объект BrandDto с информацией о бренде для сохранения.
     * @return Объект BrandDto с информацией о сохраненном бренде.
     */
    @PostMapping
    public BrandDto save(@RequestBody @Valid BrandDto brandDto) {
        log.info("POST request: Save brand - name: {}", brandDto.getName());
        return brandService.save(brandDto);
    }

    /**
     * Удаляет бренд по его идентификатору.
     *
     * @param id Идентификатор бренда для удаления.
     */
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        log.info("DELETE request: Remove brand by ID - id: {}", id);
        brandService.remove(id);
    }
}
