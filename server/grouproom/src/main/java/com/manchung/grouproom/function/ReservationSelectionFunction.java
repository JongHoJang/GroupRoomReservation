package com.manchung.grouproom.function;

import com.manchung.grouproom.batch.ReservationSelectionTasklet;
import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationSelectionFunction implements Supplier<String> {
    private final ReservationRepository reservationRepository;
    @Override
    public String get() {
        LocalDate sunday = LocalDate.now().with(DayOfWeek.SUNDAY);
        List<Reservation> reservations = reservationRepository.findByUseDateAndState(sunday, ReservationState.PENDING);

        Map<Room, List<Reservation>> reservationsByRoom = reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRoom));

        Random random = new Random();
        reservationsByRoom.forEach((room, roomReservations) -> {
            if (!roomReservations.isEmpty()) {
                Reservation winner = roomReservations.get(random.nextInt(roomReservations.size()));
                winner.setState(ReservationState.SUCCESS);
                reservationRepository.save(winner);

                roomReservations.stream()
                        .filter(reservation -> !reservation.equals(winner))
                        .forEach(loser -> {
                            loser.setState(ReservationState.FAILED);
                            reservationRepository.save(loser);
                        });
            }
        });
        return "추첨이 성공했습니다";
    }
}
