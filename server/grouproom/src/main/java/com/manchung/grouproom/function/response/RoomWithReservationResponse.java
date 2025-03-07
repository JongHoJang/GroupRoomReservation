package com.manchung.grouproom.function.response;

import com.manchung.grouproom.entity.enums.AvailableStatus;
import com.manchung.grouproom.entity.enums.SittingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomWithReservationResponse {
    private Integer roomId;
    private String name;
    private String floor;
    private Integer personAffordableCount;
    private Integer groupAffordableCount;
    private AvailableStatus availableStatus;
    private SittingType sittingType;
    private Long reservationCount;
}
