package com.manchung.grouproom.entity.enums.converter;

import com.manchung.grouproom.entity.enums.AvailableStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AvailableStatusConverter implements AttributeConverter<AvailableStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AvailableStatus status) {
        return (status != null) ? status.getCode() : null;
    }

    @Override
    public AvailableStatus convertToEntityAttribute(Integer code) {
        return (code != null) ? AvailableStatus.fromCode(code) : null;
    }
}
