package com.manchung.grouproom.controller;

import com.manchung.grouproom.function.ReservationSelectionFunction;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationSelectionFunction reservationSelectionFunction;

    @Operation(
            summary = "예약 추첨",
            description =
            """
            예약된 예약들 중 추첨합니다\n
            주의! spring batch가 작동하지 않을때만 수동 사용
            """
    )
    @PutMapping("/reservation/select")
    public String selectReservation() {
        return reservationSelectionFunction.get();
    }
}
