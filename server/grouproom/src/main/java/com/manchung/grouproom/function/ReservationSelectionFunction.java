package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationSelectionFunction implements Supplier<String> {
    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public String get() {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);
        List<Reservation> reservations = reservationRepository.findByUseDateAndState(sunday, ReservationState.PENDING);

        Map<Room, List<Reservation>> reservationsByRoom = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRoom));

        Random random = new Random();
        reservationsByRoom.forEach((room, roomReservations) -> {
            int maxWinners = room.getGroupAffordableCount();

            if (roomReservations.size() <= maxWinners) {
                // 전원 당첨
                roomReservations.forEach(reservation -> {
                    reservation.setState(ReservationState.SUCCESS);
                    reservationRepository.save(reservation);
                });
            } else {
                // 랜덤으로 maxWinners 명만 당첨
                List<Reservation> shuffled = new ArrayList<>(roomReservations);
                Collections.shuffle(shuffled, random);

                List<Reservation> winners = shuffled.subList(0, maxWinners);
                List<Reservation> losers = shuffled.subList(maxWinners, shuffled.size());

                winners.forEach(winner -> {
                    winner.setState(ReservationState.SUCCESS);
                    reservationRepository.save(winner);
                });

                losers.forEach(loser -> {
                    loser.setState(ReservationState.FAILED);
                    reservationRepository.save(loser);
                });
            }
        });
        return "추첨이 성공했습니다";
    }
}
