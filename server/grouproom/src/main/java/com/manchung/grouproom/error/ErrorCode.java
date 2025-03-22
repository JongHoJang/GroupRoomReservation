package com.manchung.grouproom.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // ✅ 인증 관련 오류
    ACCESS_TOKEN_EXPIRED(10001, HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(10002, HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(10003, HttpStatus.UNAUTHORIZED, "Refresh Token이 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(10004, HttpStatus.UNAUTHORIZED, "Refresh Token이 만료되었습니다."),

    // ✅ 회원 관련 오류
    USER_NOT_FOUND(20001, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_EMAIL(20002, HttpStatus.CONFLICT, "해당 이메일의 사용자가 없습니다."),
    PASSWORD_MISMATCH(20003, HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    SIGNUP_WRONG_INFORMATION(20003, HttpStatus.UNAUTHORIZED, "입력하신 정보가 올바르지 않습니다."),

    // ✅ 예약 관련 오류
    ROOM_NOT_FOUND(30001, HttpStatus.NOT_FOUND, "해당 룸을 찾을 수 없습니다."),
    ROOM_ALREADY_RESERVED(30002, HttpStatus.CONFLICT, "이미 예약된 룸입니다."),
    RESERVATION_NOT_ALLOWED(30003, HttpStatus.FORBIDDEN, "예약할 수 없는 사용자입니다."),
    RESERVATION_ONLY_ALLOWED_MONDAY(30003, HttpStatus.FORBIDDEN, "신청은 월요일에만 가능합니다."),
    RESERVATION_NOT_ALLOWED_TIME(30003, HttpStatus.FORBIDDEN, "신청 가능 시간이 아닙니다. (월요일 00:00~21:00)"),
    RESERVATION_NOT_ALLOWED_STATUS(30003, HttpStatus.FORBIDDEN, "이미 신청했거나 신청할 수 없는 상태입니다."),

    // ✅ 서버 내부 오류
    INTERNAL_SERVER_ERROR(99999, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생");

    private final int code;
    private final HttpStatus status;
    private final String message;
}
