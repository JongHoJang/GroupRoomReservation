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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class UserUsageStatusFunction implements Function<Integer, UserUsageStatusResponse> {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Override
    public UserUsageStatusResponse apply(Integer userId) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        LocalDateTime applicationDeadline = monday.atTime(21, 0);
        LocalDateTime announcementTime = monday.atTime(21, 5);
        LocalDateTime now = LocalDateTime.now();

        log.info("[User Usage Status] Checking usage status for userId={} at {}", userId, now);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("[User Usage Status] User not found: userId={}", userId);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        log.info("[User Usage Status] User found: name={}", user.getName());

        Optional<Reservation> reservationOpt = reservationRepository.findByUserAndUseDate(user, sunday);
        reservationOpt.ifPresentOrElse(
                r -> log.info("[User Usage Status] Reservation found: room={}, state={}", r.getRoom().getName(), r.getState()),
                () -> log.info("[User Usage Status] No reservation found for this week")
        );

        UserUsageStatus status = determineUserUsageStatus(now, applicationDeadline, announcementTime, reservationOpt);
        log.info("[User Usage Status] Determined status for userId={}: {}", userId, status);

        String roomName = reservationOpt.map(res -> res.getRoom().getName()).orElse(null);

        return new UserUsageStatusResponse(
                user.getName(),
                applicationDeadline,
                announcementTime,
                sunday,
                status,
                roomName
        );
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