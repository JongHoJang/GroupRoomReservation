package com.manchung.grouproom.function;

import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.CheckReservationApplicableRequest;
import com.manchung.grouproom.function.request.UserUsageStatusRequest;
import com.manchung.grouproom.function.response.CheckReservationApplicableResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class CheckReservationApplicableFunction implements Function<Integer, CheckReservationApplicableResponse> {
    private final UserUsageStatusFunction userUsageStatusFunction;

    @Override
    public CheckReservationApplicableResponse apply(Integer userId) {
        // ✅ 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // ✅ 현재 요일이 월요일인지 확인
        if (now.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new CustomException(ErrorCode.RESERVATION_ONLY_ALLOWED_MONDAY);
        }

        // ✅ 신청 가능 시간 (월요일 00:00 ~ 21:00)
        LocalDate today = now.toLocalDate();
        LocalDateTime applicationStart = LocalDateTime.of(today, LocalTime.of(0, 0));
        LocalDateTime applicationDeadline = LocalDateTime.of(today, LocalTime.of(21, 0));

        // ✅ 현재 시간이 신청 가능 시간 내에 있는지 확인
        if (now.isBefore(applicationStart) || now.isAfter(applicationDeadline)) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_TIME);
        }

        // ✅ 유저의 현재 신청 상태 가져오기
        UserUsageStatusResponse statusResponse = userUsageStatusFunction.apply(userId);

        // ✅ 미신청 상태인지 확인
        if (statusResponse.getStatus() != UserUsageStatus.BEFORE_APPLICATION) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_STATUS);
        }

        return new CheckReservationApplicableResponse("신청 가능합니다"); // 200 OK 응답 (Spring Cloud Function에서는 Void 사용 가능)
    }
}
