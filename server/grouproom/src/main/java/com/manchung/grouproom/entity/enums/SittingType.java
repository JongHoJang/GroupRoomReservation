package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SittingType {
    CHAIR(0, "의자"),
    FLOOR(1, "바닥");

    private final int code;
    private final String description;

    public static SittingType fromCode(int code) {
        return Arrays.stream(SittingType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SittingType code: " + code));
    }
}
