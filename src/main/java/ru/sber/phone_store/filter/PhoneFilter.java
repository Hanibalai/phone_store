package ru.sber.phone_store.filter;

import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.dto.PhoneFilterDto;

import java.util.List;

public interface PhoneFilter {

    boolean isApplicable(PhoneFilterDto filter);

    void apply(List<PhoneDto> phones, PhoneFilterDto filter);
}
