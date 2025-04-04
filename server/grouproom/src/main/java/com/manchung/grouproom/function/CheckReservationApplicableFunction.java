package com.manchung.grouproom.function;

import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.response.CheckReservationApplicableResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class CheckReservationApplicableFunction implements Function<Integer, CheckReservationApplicableResponse> {

    private final UserUsageStatusFunction userUsageStatusFunction;

    @Override
    public CheckReservationApplicableResponse apply(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        log.info("[Reservation Check] userId={}, now={}", userId, now);

        if (now.getDayOfWeek() != DayOfWeek.MONDAY) {
            log.warn("[Reservation Denied] Today is not Monday. Current day: {}", now.getDayOfWeek());
            throw new CustomException(ErrorCode.RESERVATION_ONLY_ALLOWED_MONDAY);
        }

        LocalDate today = now.toLocalDate();
        LocalDateTime applicationStart = LocalDateTime.of(today, LocalTime.of(0, 0));
        LocalDateTime applicationDeadline = LocalDateTime.of(today, LocalTime.of(21, 0));

        if (now.isBefore(applicationStart) || now.isAfter(applicationDeadline)) {
            log.warn("[Reservation Denied] Current time is outside application period. Start: {}, Deadline: {}, Now: {}",
                    applicationStart, applicationDeadline, now);
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_TIME);
        }

        log.info("[Reservation Check] Fetching user application status: userId={}", userId);
        UserUsageStatusResponse statusResponse = userUsageStatusFunction.apply(userId);
        log.info("[Reservation Check] Retrieved status: {}", statusResponse.getStatus());

        if (statusResponse.getStatus() != UserUsageStatus.BEFORE_APPLICATION) {
            log.warn("[Reservation Denied] Invalid user status for application. Status: {}", statusResponse.getStatus());
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_STATUS);
        }

        log.info("[Reservation Allowed] userId={} is eligible to apply.", userId);
        return new CheckReservationApplicableResponse("Application is allowed");
    }
}