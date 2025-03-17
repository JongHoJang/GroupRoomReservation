package com.manchung.grouproom.entity.enums.converter;

import com.manchung.grouproom.entity.enums.UserRole;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        return (userRole != null) ? userRole.getCode() : null;
    }

    @Override
    public UserRole convertToEntityAttribute(Integer code) {
        return (code != null) ? UserRole.fromCode(code) : null;
    }
}
