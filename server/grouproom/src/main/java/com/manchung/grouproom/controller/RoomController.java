package com.manchung.grouproom.controller;

import com.manchung.grouproom.function.RoomReservationFunction;
import com.manchung.grouproom.function.RoomWinnerFunction;
import com.manchung.grouproom.function.RoomWithReservationInfoFunction;
import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import com.manchung.grouproom.function.response.RoomWinnerResponse;
import com.manchung.grouproom.function.response.RoomWithReservationInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manchung.grouproom.constant.HeaderConstant.USER_ID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomWithReservationInfoFunction getRoomWithReservationInfoFunction;
    private final RoomReservationFunction reserveRoomFunction;
    private final RoomWinnerFunction roomWinnerFunction;

    @Operation(
            summary = "방 예약 요청",
            description =
                    """
                    사용자가 특정 룸을 예약하는 기능입니다\n
                    에러코드\n
                    code : 20001 -> 사용자를 찾을 수 없습니다\n
                    code : 30001 -> 해당 소그룹실을 찾을 수 없습니다\n
                    code : 30004 -> 신청은 월요일에만 가능합니다\n
                    code : 30005 -> 신청 가능 시간이 아닙니다. (월요일 00:00~21:00)\n
                    code : 30006 -> 이미 신청했거나 신청할 수 없는 상태입니다\n
                    """
    )
    @PostMapping("/room/reserve")
    public RoomReservationResponse reserve(
            @RequestHeader(USER_ID) Integer userId,
            @RequestParam Integer roomId) {
        return reserveRoomFunction.apply(new RoomReservationRequest(userId, roomId));
    }

    @Operation(
            summary = "방과 예약 정보 조회",
            description =
                    """
                    모든 방에 대한 모든 예약 정보를 가져옵니다\n
                    에러코드\n
                    없음\n
                    """
    )
    @GetMapping("/room/reservation/info")
    public List<RoomWithReservationInfoResponse> getRoomInfo(
            @RequestHeader(USER_ID) Integer userId) {
        return getRoomWithReservationInfoFunction.apply(userId);
    }

    @Operation(
            summary = "이번주 소그룹실 사용자 전체 조회",
            description =
                    """
                    금주 전체 소그룹실과 각 소그룹실 사용자를 조회합니다\n
                    """
    )
    @GetMapping("/room/users")
    public List<RoomWinnerResponse> allRoomUsers(
            @RequestHeader(USER_ID) Integer userId
    ) {
        return roomWinnerFunction.apply(userId);
    }
}
