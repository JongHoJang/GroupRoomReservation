package com.manchung.grouproom.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReservationState {
    PENDING(0, "대기 중"),
    SUCCESS(1, "예약 성공"),
    FAILED(2, "예약 실패");

    private final int code;
    private final String description;

    public static ReservationState fromCode(int code) {
        return Arrays.stream(ReservationState.values())
                .filter(state -> state.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ReservationState code: " + code));
    }
}