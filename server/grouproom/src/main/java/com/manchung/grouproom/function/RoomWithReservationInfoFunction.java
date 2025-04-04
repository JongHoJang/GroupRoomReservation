package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.AvailableStatus;
import com.manchung.grouproom.function.request.RoomWithReservationInfoRequest;
import com.manchung.grouproom.function.response.RoomWithReservationInfoResponse;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class RoomWithReservationInfoFunction implements Function<Integer, List<RoomWithReservationInfoResponse>> {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<RoomWithReservationInfoResponse> apply(Integer userId) {
        LocalDate today = LocalDate.now();
        LocalDate sunday = today.with(DayOfWeek.SUNDAY);
        log.info("[Room With Reservation Info] userId={}, reference useDate={}", userId, sunday);

        List<Room> rooms = roomRepository.findAll();
        log.info("[Room With Reservation Info] Total rooms found: {}", rooms.size());

        List<Reservation> reservations = reservationRepository.findByUseDate(sunday);
        log.info("[Room With Reservation Info] Total reservations on {}: {}", sunday, reservations.size());

        Map<Integer, Long> reservationCountMap = reservations.stream()
                .collect(Collectors.groupingBy(res -> res.getRoom().getRoomId(), Collectors.counting()));

        List<RoomWithReservationInfoResponse> response = rooms.stream()
                .filter(room -> room.getAvailableStatus().equals(AvailableStatus.AVAILABLE))
                .map(room -> {
                    long count = reservationCountMap.getOrDefault(room.getRoomId(), 0L);
                    log.info("[Room With Reservation Info] room='{}', reservationCount={}", room.getName(), count);

                    return new RoomWithReservationInfoResponse(
                            room.getRoomId(),
                            room.getName(),
                            room.getFloor(),
                            room.getPersonAffordableCount(),
                            room.getGroupAffordableCount(),
                            room.getAvailableStatus(),
                            room.getSittingType(),
                            count
                    );
                })
                .collect(Collectors.toList());

        log.info("[Room With Reservation Info] Response prepared. Total available rooms: {}", response.size());
        return response;
    }
}