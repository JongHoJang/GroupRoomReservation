import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	java
	id("org.springframework.boot") version "2.7.18"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.manchung"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}


repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	// ✅ Spring Boot 기본 스타터
	implementation("org.springframework.boot:spring-boot-starter")

	// ✅ 암호화
	implementation("org.springframework.boot:spring-boot-starter-security")

	// ✅ SpringDoc OpenAPI (Swagger UI) 의존성
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

	// ✅ Spring Boot 웹 애플리케이션 (REST API 포함)
	implementation("org.springframework.boot:spring-boot-starter-web")

	// ✅ JSON Web Token (JWT) 관련 라이브러리 (토큰 검증)
	implementation("com.auth0:java-jwt:4.3.0")

	// ✅ Spring Cloud Function (AWS Lambda 지원)
	implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.5")
	implementation("org.springframework.cloud:spring-cloud-function-context:3.2.5")
	implementation("org.springframework.cloud:spring-cloud-starter-function-web:3.2.5")

	// ✅ AWS Lambda Java SDK 추가
	implementation("com.amazonaws:aws-lambda-java-core:1.2.2")

	// ✅ AWS Lambda 이벤트 관련 라이브러리 (선택 사항)
	implementation("com.amazonaws:aws-lambda-java-events:3.10.0")

	// ✅ (선택) AWS Lambda 로그 로깅
	implementation("com.amazonaws:aws-lambda-java-log4j2:1.5.1")

	// ✅ Spring Data JPA (MySQL과 연동)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// ✅ MySQL 드라이버 (로컬 및 AWS RDS 연동)
	implementation("mysql:mysql-connector-java:8.0.33")

	implementation("com.google.guava:guava:26.0-jre")

	implementation("com.auth0:java-jwt:4.3.0")

	// ✅ Lombok (코드 간소화)
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// ✅ Spring Batch 기본 의존성
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	// ✅ 테스트 관련 의존성
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// ✅ 테스트용 메모리 DB (H2)
	testImplementation("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	named<ShadowJar>("shadowJar") {
		archiveBaseName.set("shadow")
		mergeServiceFiles()
		manifest {
			attributes(mapOf("Main-Class" to "com.manchung.grouproom.GrouproomApplication"))
		}
	}
}

tasks {
	build {
		dependsOn(shadowJar)
	}
}

