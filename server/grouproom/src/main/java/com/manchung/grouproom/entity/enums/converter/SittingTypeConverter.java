package com.manchung.grouproom.entity.enums.converter;


import com.manchung.grouproom.entity.enums.SittingType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SittingTypeConverter implements AttributeConverter<SittingType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SittingType type) {
        return (type != null) ? type.getCode() : null;
    }

    @Override
    public SittingType convertToEntityAttribute(Integer code) {
        return (code != null) ? SittingType.fromCode(code) : null;
    }
}