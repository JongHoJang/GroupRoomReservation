package com.manchung.grouproom.repository;

import com.manchung.grouproom.entity.Reservation;
import com.manchung.grouproom.entity.Room;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.ReservationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // ✅ use_date가 금주 일요일인 예약만 조회
    List<Reservation> findByUseDate(LocalDate useDate);

    Optional<Reservation> findByUserAndUseDate(User user, LocalDate useDate);

    List<Reservation> findByUseDateAndState(LocalDate useDate, ReservationState state);
}
