package ru.sber.phone_store.filter;

import org.springframework.stereotype.Component;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;
import java.util.Objects;

/**
 * Реализация интерфейса {@link PhoneFilter} для фильтрации телефонов по максимальной цене.
 */
@Component
public class PhoneMaxPriceFilter implements PhoneFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(PhoneFilterDto filter) {
        return Objects.nonNull(filter.getMaxPrice());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(List<PhoneDto> phones, PhoneFilterDto filter) {
        phones.removeIf(phoneDto -> phoneDto.getPrice() >= filter.getMaxPrice());
    }
}
