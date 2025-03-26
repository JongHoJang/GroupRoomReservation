package com.manchung.grouproom.controller;

import com.manchung.grouproom.Security.JwtProvider;
import com.manchung.grouproom.function.*;
import com.manchung.grouproom.function.request.*;
import com.manchung.grouproom.function.response.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manchung.grouproom.constant.HeaderConstant.USER_ID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GroupRoomController {
    private final SignUpFunction signUpFunction;
    private final LoginFunction loginFunction;
    private final CheckReservationApplicableFunction checkReservationAppliableFunction;
    private final RoomWithReservationInfoFunction getRoomWithReservationInfoFunction;
    private final RoomReservationFunction reserveRoomFunction;
    private final UserUsageStatusFunction userUsageStatusFunction;
    private final ReservationSelectionFunction reservationSelectionFunction;
    private final GetAccessTokenFromRefreshTokenFunction getAccessTokenFromRefreshTokenFunction;

    @Operation(
            summary = "액세스 토큰 조회",
            description =
            """
            리프레시 토큰을 이용하여 액세스 토큰을 조회합니다\n
            에러코드\n
            code : 10001 -> Access Token이 만료되었습니다.\n
            code : 10002 -> 잘못된 Access Token입니다.\n
            code : 10003 -> Refresh Token이 존재하지 않습니다.\n
            code : 10004 -> Refresh Token이 만료되었습니다.\n
            """
    )
    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshAccessToken(
            @RequestHeader(USER_ID) Integer userId,
            @RequestBody RefreshTokenRequest request) {
        return getAccessTokenFromRefreshTokenFunction.apply(request);
    }

    @Operation(
            summary = "회원가입",
            description =
            """
            사용자를 등록하고 이메일 및 비밀번호를 저장합니다\n
            에러코드\n
            code : 20004 -> 입력하신 정보가 올바르지 않습니다\n
            """
    )
    @PostMapping("/signUp")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        return signUpFunction.apply(request);
    }

    @Operation(
            summary = "로그인",
            description =
            """
            이메일과 비밀번호로 로그인하여 JWT 토큰을 반환합니다\n
            에러코드\n
            code : 20002 -> 해당 이메일의 사용자가 없습니다\n
            code : 20003 -> 비밀번호가 일치하지 않습니다\n
            """
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginFunction.apply(request);
    }

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
    @PostMapping("/reserveRoom")
    public RoomReservationResponse reserve(
            @RequestHeader(USER_ID) Integer userId,
            @RequestParam Integer roomId) {
        return reserveRoomFunction.apply(new RoomReservationRequest(userId, roomId));
    }

    @Operation(
            summary = "예약 가능 여부 확인",
            description =
            """
            해당 사용자가 특정 룸을 예약할 수 있는지 확인합니다\n
            에러코드\n
            code : 30004 -> 신청은 월요일에만 가능합니다\n
            code : 30005 -> 신청 가능 시간이 아닙니다. (월요일 00:00~21:00)\n
            code : 30006 -> 이미 신청했거나 신청할 수 없는 상태입니다\n
            """
    )
    @GetMapping("/checkReservationApplicable")
    public CheckReservationApplicableResponse checkReservation(
            @RequestHeader(USER_ID) Integer userId) {
        return checkReservationAppliableFunction.apply(userId);
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
    @GetMapping("/getRoomWithReservationInfo")
    public List<RoomWithReservationInfoResponse> getRoomInfo(
            @RequestHeader(USER_ID) Integer userId) {
        return getRoomWithReservationInfoFunction.apply(userId);
    }

    @Operation(
            summary = "사용자 이용 상태 조회",
            description =
            """
            사용자의 이용 상태를 조회합니다\n
            에러코드\n
            code : 20001 -> 사용자를 찾을 수 없습니다\n
            """
    )
    @GetMapping("/getUserUsageStatus")
    public UserUsageStatusResponse usageStatus(
            @RequestHeader(USER_ID) Integer userId
    ) {
        return userUsageStatusFunction.apply(userId);
    }

    @Operation(
            summary = "예약 추첨",
            description = "예약된 예약들 중 추첨합니다"
    )
    @PutMapping("/selectReservation")
    public String selectReservation() {
        return reservationSelectionFunction.get();
    }
}
