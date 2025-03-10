package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.function.response.RoomWithReservationInfoResponse;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomWithReservationInfoFunction implements Function<Void, List<RoomWithReservationInfoResponse>> {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<RoomWithReservationInfoResponse> apply(Void unused) {

        LocalDate today = LocalDate.now();
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);

        List<Room> rooms = roomRepository.findAll();

        List<Reservation> reservations = reservationRepository.findByUseDate(sunday);

        Map<Integer, Long> reservationCountMap = reservations.stream()
                .collect(Collectors.groupingBy(res -> res.getRoom().getRoomId(), Collectors.counting()));

        return rooms.stream().map(room -> new RoomWithReservationInfoResponse(
                room.getRoomId(),
                room.getName(),
                room.getFloor(),
                room.getPersonAffordableCount(),
                room.getGroupAffordableCount(),
                room.getAvailableStatus(),
                room.getSittingType(),
                reservationCountMap.getOrDefault(room.getRoomId(), 0L) // 예약이 없으면 0으로 설정
        )).collect(Collectors.toList());
    }
}
