package com.manchung.grouproom.entity.enums.converter;

import com.manchung.grouproom.entity.enums.ReservationState;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReservationStateConverter implements AttributeConverter<ReservationState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReservationState state) {
        return (state != null) ? state.getCode() : null;
    }

    @Override
    public ReservationState convertToEntityAttribute(Integer code) {
        return (code != null) ? ReservationState.fromCode(code) : null;
    }
}
