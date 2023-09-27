package ru.sber.phone_store.filter;

import org.springframework.stereotype.Component;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;
import java.util.Objects;

/**
 * Реализация интерфейса {@link PhoneFilter} для фильтрации телефонов по бренду.
 */
@Component
public class PhoneBrandFilter implements PhoneFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(PhoneFilterDto filter) {
        return Objects.nonNull(filter.getBrandId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(List<PhoneDto> phones, PhoneFilterDto filter) {
        phones.removeIf(phone -> !Objects.equals(phone.getBrandId(), filter.getBrandId()));
    }
}
