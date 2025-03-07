package com.manchung.grouproom;

import com.manchung.grouproom.function.LoginFunction;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
}
