plugins {
    id("org.springframework.boot") version "2.7.18"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.noarg") version "1.8.22"
    kotlin("multiplatform") version "1.8.22" apply false
    kotlin("plugin.serialization") version "1.8.22" apply false
}


group = "com.jn"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java { toolchain { languageVersion.set(JavaLanguageVersion.of(11)) } }
kotlin { jvmToolchain(11) }

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation(project(":shared-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.xerial:sqlite-jdbc:3.45.1.0")
    implementation("com.github.gwenn:sqlite-dialect:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
