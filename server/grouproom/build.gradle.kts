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

	// ✅ SpringDoc OpenAPI (Swagger UI) 의존성
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// ✅ AWS Cognito 관련 (사용 중이라면 필요)
	implementation("com.amazonaws:aws-java-sdk-cognitoidp:1.12.496")

	// ✅ Spring Cloud Function (AWS Lambda 지원)
	implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.4")
	implementation("org.springframework.cloud:spring-cloud-function-context:4.1.0")
	implementation("org.springframework.cloud:spring-cloud-starter-function-web:4.1.0")

	// ✅ Spring Data JPA (MySQL과 연동)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// ✅ MySQL 드라이버 (로컬 및 AWS RDS 연동)
	implementation("mysql:mysql-connector-java:8.0.33")

	// ✅ Lombok (코드 간소화)
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// ✅ Spring Security 추가 (비밀번호 암호화 지원)
	implementation("org.springframework.boot:spring-boot-starter-security")

	// ✅ Spring Batch 기본 의존성
	implementation("org.springframework.boot:spring-boot-starter-batch")

	// ✅ 테스트 관련 의존성
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
