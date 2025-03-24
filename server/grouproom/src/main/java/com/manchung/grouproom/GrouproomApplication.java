package com.manchung.grouproom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class GrouproomApplication {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${jwt.secret}")
	private String jwtSecret;

	public static void main(String[] args) {
		SpringApplication.run(GrouproomApplication.class, args);
	}

	@PostConstruct
	public void logs() {
		log.info(">> DB URL: {}", dbUrl);
		log.info(">> JWT_SECRET: {}", jwtSecret);
	}

//	@Bean
//	public Function<LoginRequest, LoginResponse> login(LoginFunction loginFunction) {
//		return loginFunction::apply;
//	}
//
//	@Bean
//	public Function<RoomWithReservationInfoRequest, List<RoomWithReservationInfoResponse>> getRoomWithReservationInfo(RoomWithReservationInfoFunction roomFunction) {
//		return roomFunction::apply;
//	}
//
//	@Bean
//	public Function<SignUpRequest, SignUpResponse> signUp(SignUpFunction signupFunction) {
//		return signupFunction::apply;
//	}
//
//	@Bean
//	public Function<UserUsageStatusRequest, UserUsageStatusResponse> getUserUsageStatus(UserUsageStatusFunction function) {
//		return function::apply;
//	}
//
//	@Bean
//	public Function<CheckReservationApplicableRequest, CheckReservationApplicableResponse> checkReservationApplicable(CheckReservationApplicableFunction function) {
//		return function::apply;
//	}
//
//	@Bean
//	public Function<RoomReservationRequest, RoomReservationResponse> reserveRoom(RoomReservationFunction function) {
//		return function::apply;
//	}
//
//	@Bean
//	public Supplier<String> selectReservation(ReservationSelectionFunction function) {
//		return function::get;
//	}
}
