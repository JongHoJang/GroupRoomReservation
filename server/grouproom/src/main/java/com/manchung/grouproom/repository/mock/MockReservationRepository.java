package com.manchung.grouproom.repository.mock;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.ReservationState;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MockReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return reservations;
    }

    public List<Reservation> findByUser(User user) {
        return reservations.stream().filter(res -> res.getUser().equals(user)).collect(Collectors.toList());
    }

    public List<Reservation> findByRoom(Room room) {
        return reservations.stream().filter(res -> res.getRoom().equals(room)).collect(Collectors.toList());
    }

    public List<Reservation> findByState(ReservationState state) {
        return reservations.stream().filter(res -> res.getState().equals(state)).collect(Collectors.toList());
    }

    public Optional<Reservation> findById(Integer reservationId) {
        return reservations.stream().filter(res -> res.getReservationId().equals(reservationId)).findFirst();
    }

    public void save(User user, Room room, LocalDateTime useDate) {
        Reservation reservation = Reservation.builder()
                .reservationId(reservations.size() + 1)
                .createdAt(LocalDateTime.now())
                .useDate(useDate)
                .room(room)
                .user(user)
                .state(ReservationState.PENDING)
                .build();
        reservations.add(reservation);
    }
}