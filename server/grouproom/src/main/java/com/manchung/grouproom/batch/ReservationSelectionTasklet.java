package com.manchung.grouproom.batch;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.repository.ReservationRepository;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@StepScope
public class ReservationSelectionTasklet implements Tasklet {
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
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

        return RepeatStatus.FINISHED;
    }
}
