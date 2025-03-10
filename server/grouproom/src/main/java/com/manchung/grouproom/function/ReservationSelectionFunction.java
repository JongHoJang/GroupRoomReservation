package com.manchung.grouproom.function;

import com.manchung.grouproom.batch.ReservationSelectionTasklet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class ReservationSelectionFunction implements Supplier<String> {
    private final ReservationSelectionTasklet reservationSelectionTasklet;
    @Override
    public String get() {
        try {
            reservationSelectionTasklet.execute(null, null);
            return "Batch Job 실행 완료 (AWS Lambda - Function 방식)";
        } catch (Exception e) {
            return "Batch Job 실행 중 오류 발생: " + e.getMessage();
        }
    }
}
