package com.manchung.grouproom.controller;

import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.request.SignUpUpdateRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import com.manchung.grouproom.function.response.RoomWithReservationInfoResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class GrouproomController {
    private final Function<Void, List<RoomWithReservationInfoResponse>> getRoomWithReservationInfo;
    private final Function<LoginRequest, LoginResponse> login;
    private final Function<SignUpUpdateRequest, String> signUp;
    private final Function<Integer, UserUsageStatusResponse> getUserUsageStatus;
    private final Function<Integer, Void> checkReservationApplicable;
    private final Function<RoomReservationRequest, RoomReservationResponse> reserveRoom;
    private final Supplier<String> selectReservation;

    @GetMapping("/rooms")
    public List<RoomWithReservationInfoResponse> getRooms() {
        return getRoomWithReservationInfo.apply(null);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return login.apply(request);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpUpdateRequest request) {
        return signUp.apply(request);
    }

    @GetMapping("/user/usage/{userId}")
    public UserUsageStatusResponse getUserUsage(@PathVariable Integer userId) {
        return getUserUsageStatus.apply(userId);
    }

    @PostMapping("/reservation/check/{roomId}")
    public void checkReservation(@PathVariable Integer roomId) {
        checkReservationApplicable.apply(roomId);
    }

    @PostMapping("/reservation")
    public RoomReservationResponse reserveRoom(@RequestBody RoomReservationRequest request) {
        return reserveRoom.apply(request);
    }

    @GetMapping("/reservation/select")
    public String selectReservation() {
        return selectReservation.get();
    }
}
