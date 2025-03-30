package com.manchung.grouproom.function.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class RoomWinnerResponse {
    private String roomName;
    private List<WinnerInfo> winners;

    @Getter
    @Builder
    public static class WinnerInfo {
        private String userName;
        private LocalDate useDate;
    }
}
