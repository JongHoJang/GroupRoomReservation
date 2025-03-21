package com.manchung.grouproom;

import com.manchung.grouproom.function.*;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.request.RoomReservationRequest;
import com.manchung.grouproom.function.request.SignUpUpdateRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import com.manchung.grouproom.function.response.RoomReservationResponse;
import com.manchung.grouproom.function.response.RoomWithReservationInfoResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class GrouproomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrouproomApplication.class, args);
	}

	@Bean
	public Function<LoginRequest, LoginResponse> login(LoginFunction loginFunction) {
		return loginFunction::apply;
	}

	@Bean
	public Function<Void, List<RoomWithReservationInfoResponse>> getRoomWithReservationInfo(RoomWithReservationInfoFunction roomFunction) {
		return roomFunction::apply;
	}

	@Bean
	public Function<SignUpUpdateRequest, String> signUp(SignUpFunction signupFunction) {
		return signupFunction::apply;
	}

	@Bean
	public Function<Integer, UserUsageStatusResponse> getUserUsageStatus(UserUsageStatusFunction function) {
		return function::apply;
	}

	@Bean
	public Function<Integer, Void> checkReservationApplicable(CheckReservationAppliableFunction function) {
		return function::apply;
	}

	@Bean
	public Function<RoomReservationRequest, RoomReservationResponse> reserveRoom(RoomReservationFunction function) {
		return function::apply;
	}

	@Bean
	public Supplier<String> selectReservation(ReservationSelectionFunction function) {
		return function::get;
	}
}
