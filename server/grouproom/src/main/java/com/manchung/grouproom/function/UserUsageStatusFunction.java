package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.UserUsageStatusRequest;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class UserUsageStatusFunction implements Function<Integer, UserUsageStatusResponse> {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Override
    public UserUsageStatusResponse apply(Integer userId) {
        // ✅ 현재 시간 기준으로 마감 및 발표 시간 설정
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        LocalDateTime applicationDeadline = monday.atTime(21, 0);
        LocalDateTime announcementTime = monday.atTime(21, 5);
        LocalDateTime now = LocalDateTime.now();

        // ✅ 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String userName = user.getName();
        // ✅ 금주 일요일 예약 여부 확인
        Optional<Reservation> reservationOpt = reservationRepository.findByUserAndUseDate(user, sunday);
        String roomName = reservationOpt.map(res -> res.getRoom().getName()).orElse(null);

        // ✅ 현재 상태 판단
        UserUsageStatus status = determineUserUsageStatus(now, applicationDeadline, announcementTime, reservationOpt);

        return new UserUsageStatusResponse(userName, applicationDeadline, announcementTime, sunday, status, roomName);

    }

    private UserUsageStatus determineUserUsageStatus(LocalDateTime now,
                                                     LocalDateTime applicationDeadline,
                                                     LocalDateTime announcementTime,
                                                     Optional<Reservation> reservationOpt) {
        if (now.isBefore(applicationDeadline)) {
            return reservationOpt.isPresent() ? UserUsageStatus.AFTER_APPLICATION : UserUsageStatus.BEFORE_APPLICATION;
        }

        if (now.isBefore(announcementTime)) {
            return reservationOpt.isPresent() ? UserUsageStatus.AFTER_APPLICATION : UserUsageStatus.NOT_APPLIED;
        }

        return reservationOpt.map(reservation -> {
            ReservationState reservationState = reservation.getState();
            return switch (reservationState) {
                case SUCCESS -> UserUsageStatus.WINNER;
                case FAILED -> UserUsageStatus.LOSER;
                default -> UserUsageStatus.NOT_APPLIED;
            };
        }).orElse(UserUsageStatus.NOT_APPLIED);
    }
}
