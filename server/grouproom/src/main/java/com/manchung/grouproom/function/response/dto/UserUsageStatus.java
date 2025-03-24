package com.manchung.grouproom.function.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserUsageStatus {
    NOT_APPLIED("미신청"), // 신청기간 지났는데 신청 안했음
    BEFORE_APPLICATION("신청 전"), // 신청 가능한데 신청 안한 상태
    AFTER_APPLICATION("신청 후"),
    WINNER("당첨"),
    LOSER("미당첨");

    private final String description;
}
