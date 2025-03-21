package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.request.UserUsageStatusRequest;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
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
public class RoomReservationFunction implements Function<RoomReservationRequest, RoomReservationResponse> {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserUsageStatusFunction userUsageStatusFunction;

    @Override
    public RoomReservationResponse apply(RoomReservationRequest request) {
        // ✅ 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // ✅ 현재 요일이 월요일인지 확인
        if (now.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new IllegalStateException("신청은 월요일에만 가능합니다.");
        }

        // ✅ 신청 가능 시간 (월요일 00:00 ~ 21:00)
        LocalDate today = now.toLocalDate();
        LocalDateTime applicationStart = LocalDateTime.of(today, LocalTime.of(0, 0));
        LocalDateTime applicationDeadline = LocalDateTime.of(today, LocalTime.of(21, 0));

        // ✅ 현재 시간이 신청 가능 시간 내에 있는지 확인
        if (now.isBefore(applicationStart) || now.isAfter(applicationDeadline)) {
            throw new IllegalStateException("신청 가능 시간이 아닙니다. (월요일 00:00~21:00)");
        }

        // ✅ 유저 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // ✅ 소그룹실 조회
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 존재하지 않습니다."));

        // ✅ 유저의 현재 신청 상태 가져오기
        UserUsageStatusResponse statusResponse = userUsageStatusFunction.apply(new UserUsageStatusRequest(String.valueOf(user.getUserId())));

        // ✅ 유저가 미신청 상태인지 확인
        if (statusResponse.getStatus() != UserUsageStatus.NOT_APPLIED) {
            throw new IllegalStateException("이미 신청했거나 신청할 수 없는 상태입니다.");
        }

        // ✅ 예약 생성 (기본적으로 PENDING 상태)
        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .useDate(today.with(DayOfWeek.SUNDAY)) // ✅ 금주 일요일 날짜 설정
                .createdAt(LocalDateTime.now())
                .state(ReservationState.PENDING) // ✅ 기본적으로 대기 상태
                .build();

        reservationRepository.save(reservation);

        return new RoomReservationResponse(
                "소그룹실 신청이 완료되었습니다!",
                room.getName()
        );
    }
}
