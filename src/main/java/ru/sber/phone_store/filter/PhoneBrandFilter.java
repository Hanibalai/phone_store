package ru.sber.phone_store.filter;

import org.springframework.stereotype.Component;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;
import java.util.Objects;

@Component
public class PhoneBrandFilter implements PhoneFilter {

    @Override
    public boolean isApplicable(PhoneFilterDto filter) {
        return Objects.nonNull(filter.getBrandId());
    }

    @Override
    public void apply(List<PhoneDto> phones, PhoneFilterDto filter) {
        phones.removeIf(phone -> !Objects.equals(phone.getBrandId(), filter.getBrandId()));
    }
}