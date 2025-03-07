package com.manchung.grouproom;

import com.manchung.grouproom.function.LoginFunction;
import com.manchung.grouproom.function.RoomWithReservationFunction;
import com.manchung.grouproom.function.SignUpFunction;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.request.SignUpRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import com.manchung.grouproom.function.response.RoomWithReservationResponse;
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
}
