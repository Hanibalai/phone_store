package ru.sber.phone_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import ru.sber.phone_store.dto.PhoneDto;
import ru.sber.phone_store.entity.Phone;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "brandId", source = "brand.id")
    PhoneDto toDto(Phone phone);

    @Mapping(target = "brand", ignore = true)
    Phone toEntity(PhoneDto phoneDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "model", source = "model", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "color", source = "color", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "price", source = "price", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget Phone phone, PhoneDto phoneDto);
}
