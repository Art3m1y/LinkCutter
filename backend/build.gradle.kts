plugins {
	java
	id("org.springframework.boot") version "3.0.4"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.diffplug.eclipse.apt") version "3.26.0"
}

group = "ru.Art3m1y"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
