package com.manchung.grouproom;

import com.manchung.grouproom.function.*;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.request.SignUpRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import com.manchung.grouproom.function.response.RoomWithReservationResponse;
import com.manchung.grouproom.function.response.UserUsageStatusResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;

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
	public Function<Void, List<RoomWithReservationResponse>> getRoomWithReservation(RoomWithReservationFunction roomFunction) {
		return roomFunction::apply;
	}

	@Bean
	public Function<SignUpRequest, String> signUp(SignUpFunction signupFunction) {
		return signupFunction::apply;
	}

	@Bean
	public Function<Integer, UserUsageStatusResponse> userUsageStatusFunction(UserUsageStatusFunction function) {
		return function::apply;
	}

	@Bean
	public Function<Integer, Void> checkReservationAppliableFunction(CheckReservationAppliableFunction function) {
		return function::apply;
	}
}
