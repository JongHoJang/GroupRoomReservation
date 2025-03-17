package com.manchung.grouproom.entity;

import com.manchung.grouproom.entity.enums.ReservationState;
import com.manchung.grouproom.entity.enums.converter.ReservationStateConverter;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "use_date", nullable = false)
    private LocalDate useDate;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Convert(converter = ReservationStateConverter.class)
    @Column(name = "state", nullable = false)
    private ReservationState state;
}