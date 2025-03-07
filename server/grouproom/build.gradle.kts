plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.manchung"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter")
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// ✅ Spring Boot 기본 스타터
	implementation("org.springframework.boot:spring-boot-starter")

	// ✅ Spring Cloud Function (AWS Lambda 지원)
	implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.4")
	implementation("org.springframework.cloud:spring-cloud-starter-function-web")

	// ✅ Spring Data JPA (MySQL과 연동)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// ✅ MySQL 드라이버 (로컬 및 AWS RDS 연동)
	implementation("mysql:mysql-connector-java:8.0.33")

	// ✅ Lombok (코드 간소화)
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// ✅ 테스트 관련 의존성
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
