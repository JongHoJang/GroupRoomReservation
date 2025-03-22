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
            description = "리프레시 토큰을 이용하여 액세스 토큰을 조회합니다"
    )
    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return getAccessTokenFromRefreshTokenFunction.apply(request);
    }

    @Operation(
            summary = "회원가입",
            description = "사용자를 등록하고 이메일 및 비밀번호를 저장합니다."
    )
    @PostMapping("/signUp")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        return signUpFunction.apply(request);
    }

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 반환합니다."
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginFunction.apply(request);
    }

    @Operation(
            summary = "방 예약 요청",
            description = "사용자가 특정 룸을 예약하는 기능입니다."
    )
    @PostMapping("/reserveRoom")
    public RoomReservationResponse reserve(
            @RequestParam Integer userId,
            @RequestParam Integer roomId) {
        return reserveRoomFunction.apply(new RoomReservationRequest(userId, roomId));
    }

    @Operation(
            summary = "예약 가능 여부 확인",
            description = "해당 사용자가 특정 룸을 예약할 수 있는지 확인합니다."
    )
    @GetMapping("/checkReservationApplicable")
    public CheckReservationApplicableResponse checkReservation(
            @RequestParam Integer userId) {
        return checkReservationAppliableFunction.apply(userId);
    }

    @Operation(
            summary = "방과 예약 정보 조회",
            description = "모든 방에 대한 모든 예약 정보를 가져옵니다."
    )
    @GetMapping("/getRoomWithReservationInfo")
    public List<RoomWithReservationInfoResponse> getRoomInfo() {
        return getRoomWithReservationInfoFunction.get();
    }

    @Operation(
            summary = "사용자 이용 상태 조회",
            description = "사용자의 이용 상태를 조회합니다."
    )
    @GetMapping("/getUserUsageStatus")
    public UserUsageStatusResponse usageStatus(
            @RequestParam Integer userId
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
