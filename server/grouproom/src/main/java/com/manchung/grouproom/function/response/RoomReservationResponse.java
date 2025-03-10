package com.manchung.grouproom.function.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationResponse {
    private String message;
    private String roomName;
}
