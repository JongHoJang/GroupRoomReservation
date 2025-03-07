package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AvailableStatus {

    UNAVAILABLE(0, "사용 불가"),
    AVAILABLE(1, "사용 가능");

    private final int code;
    private final String description;

    public static AvailableStatus fromCode(int code) {
        return Arrays.stream(AvailableStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid AvailableStatus code: " + code));
    }
}
