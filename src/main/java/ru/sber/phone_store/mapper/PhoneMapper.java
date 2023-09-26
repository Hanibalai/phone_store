package ru.sber.phone_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.entity.Phone;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "brandId", source = "brand.id")
    PhoneDto toDto(Phone phone);

    @Mapping(target = "brand", ignore = true)
    Phone toEntity(PhoneDto phoneDto);
}
