package ru.sber.phone_store.filter;

import org.springframework.stereotype.Component;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;
import java.util.Objects;

@Component
public class PhoneMaxPriceFilter implements PhoneFilter {

    @Override
    public boolean isApplicable(PhoneFilterDto filter) {
        return Objects.nonNull(filter.getMaxPrice());
    }

    @Override
    public void apply(List<PhoneDto> phones, PhoneFilterDto filter) {
        phones.removeIf(phoneDto -> phoneDto.getPrice() >= filter.getMaxPrice());
    }
}
