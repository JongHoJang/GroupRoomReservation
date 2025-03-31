package com.manchung.grouproom.controller;

import com.manchung.grouproom.function.ReservationSelectionFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationJobScheduler {
    private final ReservationSelectionFunction reservationSelectionFunction;

    @Scheduled(cron = "0 1 21 * * MON")
    public void runReservationSchedule() {
        try {
            reservationSelectionFunction.get();
            log.info("✅ [배치] 예약 배치 정상 실행 완료");
        } catch (Exception e) {
            log.error("❌ [배치] 예약 배치 실행 중 오류 발생", e);
        }
    }
}
