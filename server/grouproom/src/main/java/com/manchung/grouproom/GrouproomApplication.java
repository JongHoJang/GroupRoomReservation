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
}
