package ru.sber.phone_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.sber.phone_store.dto.BrandDto;
import ru.sber.phone_store.entity.Brand;
import ru.sber.phone_store.entity.Phone;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "phoneIds", source = "phones", qualifiedByName = "toPhoneIds")
    BrandDto toDto(Brand brand);

    @Mapping(target = "phones", ignore = true)
    Brand toEntity(BrandDto brandDto);

    @Named("toPhoneIds")
    default List<Long> toPhoneIds(List<Phone> phones) {
        if (phones == null) {
            return null;
        }
        return phones.stream().map(Phone::getId).toList();
    }
}
