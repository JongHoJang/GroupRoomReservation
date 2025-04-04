package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.function.response.RoomWinnerResponse;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class RoomWinnerFunction implements Function<Integer, List<RoomWinnerResponse>> {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<RoomWinnerResponse> apply(Integer userId) {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);
        log.info("[Room Winner] Fetching winners for useDate={}", sunday);

        List<Reservation> reservations = reservationRepository.findByUseDateAndState(sunday, ReservationState.SUCCESS);
        log.info("[Room Winner] Total winning reservations found: {}", reservations.size());

        Map<Integer, List<Reservation>> reservationMap = reservations.stream()
                .collect(Collectors.groupingBy(r -> r.getRoom().getRoomId()));

        List<RoomWinnerResponse> result = roomRepository.findAll().stream()
                .map(room -> {
                    List<Reservation> winnerReservations = reservationMap.getOrDefault(room.getRoomId(), Collections.emptyList());
                    log.info("[Room Winner] room='{}', winners={}", room.getName(), winnerReservations.size());

                    List<RoomWinnerResponse.WinnerInfo> winnerInfos = winnerReservations.stream()
                            .map(r -> RoomWinnerResponse.WinnerInfo.builder()
                                    .userName(r.getUser().getName())
                                    .useDate(r.getUseDate())
                                    .build())
                            .collect(Collectors.toList());

                    return RoomWinnerResponse.builder()
                            .roomName(room.getName())
                            .winners(winnerInfos)
                            .build();
                })
                .collect(Collectors.toList());

        log.info("[Room Winner] Room winner mapping completed. Total rooms={}", result.size());
        return result;
    }
}