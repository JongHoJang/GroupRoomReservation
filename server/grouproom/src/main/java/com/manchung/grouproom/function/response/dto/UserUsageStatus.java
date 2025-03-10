package com.manchung.grouproom.function.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserUsageStatus {
    NOT_APPLIED("미신청"),
    BEFORE_APPLICATION("신청 전"),
    AFTER_APPLICATION("신청 후"),
    WINNER("당첨"),
    LOSER("미당첨");

    private final String description;
}
