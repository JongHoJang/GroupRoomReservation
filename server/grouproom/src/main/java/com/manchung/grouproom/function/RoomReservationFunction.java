package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class RoomReservationFunction implements Function<RoomReservationRequest, RoomReservationResponse> {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserUsageStatusFunction userUsageStatusFunction;

    @Transactional
    @Override
    public RoomReservationResponse apply(RoomReservationRequest request) {
        LocalDateTime now = LocalDateTime.now();
        log.info("[Room Reservation] userId={}, roomId={}, requestTime={}", request.getUserId(), request.getRoomId(), now);

        if (now.getDayOfWeek() != DayOfWeek.MONDAY) {
            log.warn("[Room Reservation] Reservation not allowed - today is not Monday. Day={}", now.getDayOfWeek());
            throw new CustomException(ErrorCode.RESERVATION_ONLY_ALLOWED_MONDAY);
        }

        LocalDate today = now.toLocalDate();
        LocalDateTime applicationStart = LocalDateTime.of(today, LocalTime.of(0, 0));
        LocalDateTime applicationDeadline = LocalDateTime.of(today, LocalTime.of(21, 0));

        if (now.isBefore(applicationStart) || now.isAfter(applicationDeadline)) {
            log.warn("[Room Reservation] Reservation not allowed - outside application time. Now={}", now);
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_TIME);
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    log.warn("[Room Reservation] User not found. userId={}", request.getUserId());
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> {
                    log.warn("[Room Reservation] Room not found. roomId={}", request.getRoomId());
                    return new CustomException(ErrorCode.ROOM_NOT_FOUND);
                });

        log.info("[Room Reservation] Checking user application status. userId={}", user.getUserId());
        UserUsageStatusResponse statusResponse = userUsageStatusFunction.apply(user.getUserId());
        log.info("[Room Reservation] Retrieved user status: {}", statusResponse.getStatus());

        if (statusResponse.getStatus() != UserUsageStatus.BEFORE_APPLICATION) {
            log.warn("[Room Reservation] Reservation not allowed - invalid user status. Status={}", statusResponse.getStatus());
            throw new CustomException(ErrorCode.RESERVATION_NOT_ALLOWED_STATUS);
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .useDate(today.with(DayOfWeek.SUNDAY))
                .createdAt(now)
                .state(ReservationState.PENDING)
                .build();

        reservationRepository.save(reservation);
        log.info("[Room Reservation] Reservation created. reservationId={}, userId={}, roomId={}",
                reservation.getReservationId(), user.getUserId(), room.getRoomId());

        return new RoomReservationResponse(
                "소그룹실 신청이 완료되었습니다!",
                room.getName()
        );
    }
}