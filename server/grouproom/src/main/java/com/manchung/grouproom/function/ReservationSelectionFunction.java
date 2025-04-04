package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ReservationSelectionFunction implements Supplier<String> {

    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public String get() {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);
        log.info("[Reservation Selection] Running for useDate={}", sunday);

        List<Reservation> reservations = reservationRepository.findByUseDateAndState(sunday, ReservationState.PENDING);
        log.info("[Reservation Selection] Total pending reservations found: {}", reservations.size());

        Map<Room, List<Reservation>> reservationsByRoom = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRoom));

        Random random = new Random();

        reservationsByRoom.forEach((room, roomReservations) -> {
            log.info("[Reservation Selection] Processing room='{}' (maxWinners={}) with {} applicants",
                    room.getName(), room.getGroupAffordableCount(), roomReservations.size());

            int maxWinners = room.getGroupAffordableCount();

            if (roomReservations.size() <= maxWinners) {
                roomReservations.forEach(reservation -> {
                    reservation.setState(ReservationState.SUCCESS);
                    reservationRepository.save(reservation);
                    log.info("[Reservation Selection] Winner (auto): reservationId={} userId={}",
                            reservation.getReservationId(), reservation.getUser().getUserId());
                });
            } else {
                List<Reservation> shuffled = new ArrayList<>(roomReservations);
                Collections.shuffle(shuffled, random);

                List<Reservation> winners = shuffled.subList(0, maxWinners);
                List<Reservation> losers = shuffled.subList(maxWinners, shuffled.size());

                winners.forEach(winner -> {
                    winner.setState(ReservationState.SUCCESS);
                    reservationRepository.save(winner);
                    log.info("[Reservation Selection] Winner: reservationId={} userId={}",
                            winner.getReservationId(), winner.getUser().getUserId());
                });

                losers.forEach(loser -> {
                    loser.setState(ReservationState.FAILED);
                    reservationRepository.save(loser);
                    log.info("[Reservation Selection] Loser: reservationId={} userId={}",
                            loser.getReservationId(), loser.getUser().getUserId());
                });
            }
        });

        log.info("[Reservation Selection] Selection process completed");
        return "추첨이 성공했습니다";
    }
}