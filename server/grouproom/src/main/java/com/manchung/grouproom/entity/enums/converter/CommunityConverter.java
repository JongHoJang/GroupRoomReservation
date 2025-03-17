package com.manchung.grouproom.entity.enums.converter;

import com.manchung.grouproom.entity.enums.Community;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CommunityConverter implements AttributeConverter<Community, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Community community) {
        return (community != null) ? community.getCode() : null;
    }

    @Override
    public Community convertToEntityAttribute(Integer code) {
        return (code != null) ? Community.fromCode(code) : null;
    }
}
