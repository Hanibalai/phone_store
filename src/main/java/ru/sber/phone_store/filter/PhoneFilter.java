package ru.sber.phone_store.filter;

import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;

/**
 * Интерфейс для фильтрации списка телефонов на основе параметров фильтрации.
 */

public interface PhoneFilter {

    /**
     * Определяет, применим ли данный фильтр к заданному фильтру.
     *
     * @param filter Объект {@link PhoneFilterDto} с параметрами фильтрации.
     * @return true, если фильтр применим, в противном случае - false.
     */
    boolean isApplicable(PhoneFilterDto filter);

    /**
     * Применяет фильтр к списку телефонов на основе заданного фильтра.
     *
     * @param phones Список объектов {@link PhoneDto} для фильтрации.
     * @param filter Объект {@link PhoneFilterDto} с параметрами фильтрации.
     */
    void apply(List<PhoneDto> phones, PhoneFilterDto filter);
}
