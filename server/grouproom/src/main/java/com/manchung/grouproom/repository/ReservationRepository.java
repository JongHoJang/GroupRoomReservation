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

    // ✅ 특정 사용자의 예약 조회
    List<Reservation> findByUser(User user);

    // ✅ 특정 방(Room)의 예약 조회
    List<Reservation> findByRoom(Room room);

    // ✅ 특정 예약 상태(State)로 조회
    List<Reservation> findByState(ReservationState state);

    // ✅ 특정 날짜 이후의 예약 조회
    List<Reservation> findByUseDateAfter(LocalDateTime date);

    // ✅ use_date가 금주 일요일인 예약만 조회
    List<Reservation> findByUseDate(LocalDate useDate);

    Optional<Reservation> findByUserAndUseDate(User user, LocalDate useDate);

    List<Reservation> findByUseDateAndState(LocalDate useDate, ReservationState state);
}
