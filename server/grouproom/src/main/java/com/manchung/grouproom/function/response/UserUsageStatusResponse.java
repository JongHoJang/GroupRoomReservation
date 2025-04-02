package com.manchung.grouproom.function.response;

import com.manchung.grouproom.function.response.dto.UserUsageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUsageStatusResponse {
    private String userName;
    private LocalDateTime applicationDeadline; // 신청 마감 시간
    private LocalDateTime announcementTime;    // 발표 시간
    private LocalDate useDate;                 // 소그룹실 사용일 (금주 일요일)
    private UserUsageStatus status;            // 유저 이용 상태
    private String roomName;                   // 방 이름 (당첨 또는 신청 시)
}
