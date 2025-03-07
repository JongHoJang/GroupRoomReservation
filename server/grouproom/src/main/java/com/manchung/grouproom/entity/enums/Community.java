package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Community {
    JOSEPH(1, "요셉"),
    DAVID(2, "다윗"),
    ESTHER(3, "에스더"),
    JOSHUA(4, "여호수아"),
    DANIEL(5, "다니엘"),
    MOSES(6, "모세"),
    PRISCILLA(7, "쁘아"),
    NEHEMIAH(8, "느헤미야");

    private final int code;
    private final String description;

    public static Community fromCode(int code) {
        return List.of(Community.values())
                .stream()
                .filter(community -> community.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Community code: " + code));
    }
}
