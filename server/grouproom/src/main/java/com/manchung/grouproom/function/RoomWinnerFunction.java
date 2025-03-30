package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.function.response.RoomWinnerResponse;
import com.manchung.grouproom.repository.ReservationRepository;
import com.manchung.grouproom.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomWinnerFunction implements Function<Integer, List<RoomWinnerResponse>> {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;


    @Override
    public List<RoomWinnerResponse> apply(Integer userId) {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);

        // 당첨된 예약 전체 조회
        List<Reservation> reservations = reservationRepository.findByUseDateAndState(sunday, ReservationState.SUCCESS);

        // roomId -> List<Reservation> 맵핑
        Map<Integer, List<Reservation>> reservationMap = reservations.stream()
                .collect(Collectors.groupingBy(r -> r.getRoom().getRoomId()));

        // 전체 Room 기준으로 매핑
        return roomRepository.findAll().stream()
                .map(room -> {
                    List<Reservation> winnerReservations = reservationMap.getOrDefault(room.getRoomId(), Collections.emptyList());

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
    }
}
