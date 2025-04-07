package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(0, "관리자"),
    USER(1, "일반 사용자"),
    USER_CANNOT_SIGNUP(2, "가입 불가능한 사용자");


    private final int code;
    private final String description;

    public static UserRole fromCode(int code) {
        return Stream.of(UserRole.values())
                .filter(userRole -> userRole.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role code: " + code));
    }
}
