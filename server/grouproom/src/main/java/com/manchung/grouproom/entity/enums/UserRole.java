package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER(0, "일반 사용자"),
    ADMIN(1, "관리자");

    private final int code;
    private final String description;

    public static UserRole fromCode(int code) {
        return List.of(UserRole.values())
                .stream()
                .filter(userRole -> userRole.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role code: " + code));
    }
}
